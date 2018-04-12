package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import project.models.CryptoRoot;
import project.models.HistoHour;
import project.models.HistoMinute;
import project.repositories.HistoHourRepository;
import project.repositories.HistoMinuteRepository;

import java.util.ArrayList;

@Service
public class CryptoService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    HistoMinuteRepository histoMinuteRepository;

    @Autowired
    HistoHourRepository histoHourRepository;

    public CryptoRoot search(String param, String fsym, String tsym, boolean persist) {
        String fquery = "https://min-api.cryptocompare.com/data/" + param + "?fsym="+fsym+"&tsym="+tsym;

        //mapping the data to the class
        CryptoRoot data = restTemplate.getForObject(fquery, CryptoRoot.class);

        //persisting data to DB
        if (persist){
            saveAllDataPerMinute(data, fsym, tsym);
        }
        return data;
    }

    //saving the history data to DB
//    private void saveAllDataPerMinute(CryptoRoot data, String fsym, String tsym){
//
//
//
//        //loop through the data object and set it to DB
//        for(int i = 0; i < data.getData().length; i++) {
//
//            HistoMinute obj = new HistoMinute();
//
//            obj.setFromCurrency(fsym);
//            obj.setToCurrency(tsym);
//            obj.setTime(data.getData()[i].getTime());
//            obj.setClose(data.getData()[i].getClose());
//            obj.setHigh(data.getData()[i].getHigh());
//            obj.setLow(data.getData()[i].getLow());
//            obj.setOpen(data.getData()[i].getOpen());
//            obj.setVolumefrom(data.getData()[i].getVolumefrom());
//            obj.setVolumeto(data.getData()[i].getVolumeto());
//
//            //calling the method that checks duplicate
//            if (checkDuplicate(obj) == false) {histoMinuteRepository.save(obj);}
//        }
//    }

    private void saveAllDataPerMinute(CryptoRoot data, String fsym, String tsym){



        //loop through the data object and set it to DB
        for(int i = 0; i < data.getData().length; i++) {

            HistoHour obj = new HistoHour();

            obj.setFromCurrency(fsym);
            obj.setToCurrency(tsym);
            obj.setTime(data.getData()[i].getTime());
            obj.setClose(data.getData()[i].getClose());
            obj.setHigh(data.getData()[i].getHigh());
            obj.setLow(data.getData()[i].getLow());
            obj.setOpen(data.getData()[i].getOpen());
            obj.setVolumefrom(data.getData()[i].getVolumefrom());
            obj.setVolumeto(data.getData()[i].getVolumeto());

            //calling the method that checks duplicate
            if (checkDuplicate(obj) == false) {histoHourRepository.save(obj);}
        }
    }
    //checking if there is any duplicate time in the data based on fromCurrency, toCurrency, and time
//    private boolean checkDuplicate (HistoMinute obj){
//        HistoMinute histoMinute = histoMinuteRepository.findByTimeAndFromCurrencyAndToCurrency(obj.getTime(), obj.getFromCurrency(), obj.getToCurrency());
//        if (histoMinute == null) {
//            return false;
//        }else return true;
//    }

    //checking if there is any duplicate time in the data based on fromCurrency, toCurrency, and time
    private boolean checkDuplicate (HistoHour obj){
        HistoHour histoHour = histoHourRepository.findByTimeAndFromCurrencyAndToCurrency(obj.getTime(), obj.getFromCurrency(), obj.getToCurrency());
        if (histoHour == null) {
            return false;
        }else return true;
    }

    public ArrayList<HistoMinute> getAllData(){

        return (ArrayList<HistoMinute>) histoMinuteRepository.findAll();
    }

    public ArrayList<HistoMinute> getDataByFsym(String fromCurrency){

        return histoMinuteRepository.findByFromCurrency(fromCurrency);
    }

    public ArrayList<HistoMinute> getDataByTsym(String tsym) {
        return histoMinuteRepository.findByToCurrency(tsym);
    }

    public HistoMinute getDataById(int id) {
        return histoMinuteRepository.findById(id);
    }

    public ArrayList<HistoMinute> getDataByFsymAndTsym(String fsym, String tsym){
        return histoMinuteRepository.findByFromCurrencyAndToCurrency(fsym, tsym);
    }

    public String addData(HistoMinute data) {
        histoMinuteRepository.save(data);
        return "Data inserted";
    }

    public String deleteDataById(int id) {
        histoMinuteRepository.delete(id);
        return "Data id: " + id + " deleted.";
    }

    public HistoMinute update(HistoMinute data) {
        histoMinuteRepository.save(data);
        return histoMinuteRepository.findById(data.getId());
    }


}
