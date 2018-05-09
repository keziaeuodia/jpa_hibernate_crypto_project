package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.exceptions.CryptoDataNotFoundException;
import project.exceptions.DuplicateDataException;
import project.models.CryptoRoot;
import project.models.History;
import project.services.CryptoService;

import java.util.ArrayList;

@RestController
@RequestMapping("/")

public class CryptoController {

    @Autowired
    CryptoService cryptoService;

    /**
     * making a 3rd party api call to cryptocompare
     * histominute returns open, high, low, close, volumefrom and volumeto from the each minute historical data.
     * This data is only stored for 7 days, if you need more,use the hourly or daily path.
     * @param fsym determine cryptocurrency you want to query e.g BTC
     * @param tsym determine the currency to compare it against e.g USD
     * @param persist has the default value of false, if persist=true, data requested will be persisted to DB
     * @return CryptoRoot data
     */
    @RequestMapping("data/histominute")
    public CryptoRoot searchMinute (@RequestParam(value = "fsym", required = true, defaultValue = "BTC") String fsym,
                                    @RequestParam(value = "tsym", required = true, defaultValue = "USD") String tsym,
                                    @RequestParam(value = "limit", required = false, defaultValue = "1440") int limit,
                                    @RequestParam(value = "persist", defaultValue = "false") boolean persist) {
        return cryptoService.search("histominute", fsym, tsym, limit, persist);
    }

    /**
     * making a 3rd party api call to cryptocompare
     * histohour returns open, high, low, close, volumefrom and volumeto from the each hour historical data.
     * @param fsym determine cryptocurrency you want to query e.g BTC
     * @param tsym determine the currency to compare it against e.g USD
     * @param persist has the default value of false, if persist=true, data requested will be persisted to DB
     * @return CryptoRoot data
     */
    @RequestMapping("data/histohour")
    public CryptoRoot searchHour (@RequestParam(value = "fsym", required = true, defaultValue = "BTC") String fsym,
                                  @RequestParam(value = "tsym", required = true, defaultValue = "USD") String tsym,
                                  @RequestParam(value = "limit", required = false, defaultValue = "500") int limit,
                                  @RequestParam(value = "persist", defaultValue = "false") boolean persist){
        return cryptoService.search("histohour", fsym, tsym, limit, persist);
    }

    /**
     * making a 3rd party api call to cryptocompare
     * histoday returns open, high, low, close, volumefrom and volumeto daily historical data. The values are based on 00:00 GMT time.
     * @param fsym determine cryptocurrency you want to query e.g BTC
     * @param tsym determine the currency to compare it against e.g USD
     * @param limit determine the limit of the data taken, max 2000
     * @param persist has the default value of false, if persist=true, data requested will be persisted to DB
     * @return CryptoRoot data
     */
    @RequestMapping("data/histoday")
    public CryptoRoot searchDay (@RequestParam(value = "fsym", required = true, defaultValue = "BTC") String fsym,
                                 @RequestParam(value = "tsym", required = true, defaultValue = "USD") String tsym,
                                 @RequestParam(value = "limit", required = false, defaultValue = "100") int limit,
                                 @RequestParam(value = "persist", defaultValue = "false") boolean persist){
        return cryptoService.search("histoday", fsym, tsym, limit, persist);
    }

    /**
     * getting history of crypto data from the database
     * path: /data/tsym?tsym=USD
     * @param fsym determine cryptocurrency you want to query e.g BTC
     * @param timesignal determine the time signal of the data requested, "minute", "hour", or "day",
     *                   if it is not provided, the data given will be in minute
     * @return list of History data where toCurrency = fsym
     */
    @RequestMapping(method= RequestMethod.GET, value = "/fsym")
    public ArrayList<History> findByFsym(@RequestParam(value = "fsym", required = true) String fsym,
                                         @RequestParam(value = "timesignal", required = false, defaultValue = "minute") String timesignal){
        return cryptoService.getDataByFsym(fsym, timesignal);
    }

    /**
     * getting history of crypto data from the database
     * e.g. /tsym?tsym=USD
     * @param tsym determine the currency to compare the fsym against e.g USD
     * @param timesignal determine the time signal of the data requested, "minute", "hour", or "day",
     *                   if it is not provided, the data given will be in minute
     * @return list of History data where toCurrency = tsym
     */

    @RequestMapping(method= RequestMethod.GET, value = "/tsym")
    public ArrayList<History> findByTsym(@RequestParam(value = "tsym", required = true) String tsym,
                                         @RequestParam(value = "timesignal", required = false, defaultValue = "minute") String timesignal){
        return cryptoService.getDataByTsym(tsym, timesignal);
    }

    /**
     * getting history of crypto data from the database
     * e.g. /currency?fsym=BTC&tsym=USD&timesignal=minute
     * @param fsym determine cryptocurrency you want to query e.g BTC
     * @param tsym determine the currency to compare it against e.g USD
     * @param timesignal determine the time signal of the data requested, "minute", "hour", or "day",
     *                   if it is not provided, the data given will be in minute
     * @return list of History data where toCurrency = tsym
     */
    @RequestMapping(method= RequestMethod.GET, value = "/currency")
    public ArrayList<History> findByFsymAndTsym(@RequestParam(value = "fsym", required = true) String fsym,
                                                @RequestParam(value = "tsym", required = true) String tsym,
                                                @RequestParam(value = "timesignal", required = false, defaultValue = "minute") String timesignal) {
        return cryptoService.getDataByFsymAndTsym(fsym, tsym, timesignal);
    }

    /**
     * getting history data from the database based on the id
     * e.g. /897
     * @param id
     * @return history data of that particular id
     */
    @GetMapping("/{id}")
    public History getDataById(@PathVariable(value = "id") int id) throws CryptoDataNotFoundException{
        return cryptoService.getDataById(id);
    }

    /**
     * getting all history data from the database
     * e.g /
     * @return a list of history data in the database
     */
    @GetMapping("/")
    public ArrayList<History> findAll(){
        return cryptoService.getAllData();
    }

    /**
     * using curl or postman, insert a new data object
     * e.g. /
     * @param data
     * @return
     */
    @PostMapping("/")
    public String add(@RequestBody History data) throws DuplicateDataException{
        return cryptoService.addData(data);
    }

    /**
     * using curl or postman patch a history data of the primary key (id)
     * e.g. /
     * @param data
     * @return
     */
    @PutMapping("/{id}")
    public History update(@PathVariable(value = "id") int id,
                          @RequestBody History data) throws CryptoDataNotFoundException {
        return cryptoService.update(data);
    }

    /**
     * using curl or postman delete a history data based on id
     * e.g. /879
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable(value = "id") int id) throws CryptoDataNotFoundException{
        return cryptoService.deleteDataById(id);
    }

}
