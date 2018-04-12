package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.models.CryptoRoot;
import project.models.HistoMinute;
import project.services.CryptoService;

import java.util.ArrayList;

@RestController
@RequestMapping("/data")

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
    @RequestMapping("/histominute")
    public CryptoRoot searchMinute (@RequestParam(value = "fsym", required = true, defaultValue = "BTC") String fsym,
                                    @RequestParam(value = "tsym", required = true, defaultValue = "USD") String tsym,
                                    @RequestParam(value = "persist", defaultValue = "false") boolean persist){
        return cryptoService.search("histominute", fsym, tsym, persist);
    }

    /**
     * making a 3rd party api call to cryptocompare
     * histohour returns open, high, low, close, volumefrom and volumeto from the each hour historical data.
     * @param fsym determine cryptocurrency you want to query e.g BTC
     * @param tsym determine the currency to compare it against e.g USD
     * @param persist has the default value of false, if persist=true, data requested will be persisted to DB
     * @return CryptoRoot data
     */
    @RequestMapping("/histohour")
    public CryptoRoot searchHour (@RequestParam(value = "fsym", required = true, defaultValue = "BTC") String fsym,
                                  @RequestParam(value = "tsym", required = true, defaultValue = "USD") String tsym,
                                  @RequestParam(value = "persist", defaultValue = "false") boolean persist){
        return cryptoService.search("histohour", fsym, tsym, persist);
    }

    /**
     * making a 3rd party api call to cryptocompare
     * histoday returns open, high, low, close, volumefrom and volumeto daily historical data. The values are based on 00:00 GMT time.
     * @param fsym determine cryptocurrency you want to query e.g BTC
     * @param tsym determine the currency to compare it against e.g USD
     * @param persist has the default value of false, if persist=true, data requested will be persisted to DB
     * @return CryptoRoot data
     */
    @RequestMapping("/histoday")
    public CryptoRoot searchDay (@RequestParam(value = "fsym", required = true, defaultValue = "BTC") String fsym,
                                 @RequestParam(value = "tsym", required = true, defaultValue = "USD") String tsym,
                                 @RequestParam(value = "persist", defaultValue = "false") boolean persist){
        return cryptoService.search("histoday", fsym, tsym, persist);
    }

    //get crypto data by "from" currency
//    @RequestMapping(method= RequestMethod.GET, value = "/fsym")
//    public ArrayList<HistoMinute> findByFsym(@RequestParam(value = "fsym", required = true) String fsym){
//        return cryptoService.getDataByFsym(fsym);
//    }
//
//    //get crypto data by "to" currency
//    @RequestMapping(method= RequestMethod.GET, value = "/tsym")
//    public ArrayList<HistoMinute> findByTsym(@RequestParam(value = "tsym", required = true) String tsym){
//        return cryptoService.getDataByTsym(tsym);
//    }
//
//    //get crypto data by "id"
//    @GetMapping("/{id}")
//    public HistoMinute getDataById(@PathVariable(value = "id") int id){
//        return cryptoService.getDataById(id);
//    }
//
//    //get all crypto data from the database
//    @GetMapping("/")
//    public ArrayList<HistoMinute> findAll(){
//        return cryptoService.getAllData();
//    }
//
//    //post new crypto data to DB
//    @PostMapping("/")
//    public String add(@RequestBody HistoMinute data){
//        return cryptoService.addData(data);
//    }
//
//    //edit data in DB by ID
//    @PatchMapping("/")
//    public HistoMinute update(@RequestBody HistoMinute data) {
//        return cryptoService.update(data);
//    }
//
//    //delete data in DB by ID
//    @DeleteMapping("/{id}")
//    public String deleteById(@PathVariable(value = "id") int id){
//        return cryptoService.deleteDataById(id);
//    }

}
