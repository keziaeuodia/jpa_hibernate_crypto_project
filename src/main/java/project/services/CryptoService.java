package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import project.models.*;
import project.repositories.HistoDayRepository;
import project.repositories.HistoHourRepository;
import project.repositories.HistoMinuteRepository;
import project.repositories.HistoryInterface;


import java.util.ArrayList;

@Service
public class CryptoService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    HistoMinuteRepository histoMinuteRepository;

    @Autowired
    HistoHourRepository histoHourRepository;

    @Autowired
    HistoDayRepository histoDayRepository;

    @Autowired
    HistoryInterface historyInterface;


    public CryptoRoot search(String param, String fsym, String tsym, boolean persist) {
        String fquery = "https://min-api.cryptocompare.com/data/" + param + "?fsym="+fsym+"&tsym="+tsym;

        //mapping the data to the class
        CryptoRoot data = restTemplate.getForObject(fquery, CryptoRoot.class);

        //persisting data to DB
        if (persist){
            switch (param){
                case "histominute" :
                    HistoMinute min = new HistoMinute();
                    saveAllData(data, fsym, tsym, min);
                break;

                case "histohour" :
                    HistoHour hour = new HistoHour();
                    saveAllData(data, fsym, tsym, hour);
                break;
                case "histoday" :
                    HistoDay day = new HistoDay();
                    saveAllData(data, fsym, tsym, day);
            }
        }
        return data;
    }


    private <T extends History> void saveAllData(CryptoRoot data, String fsym, String tsym, T temp) {
        for(int i = 0; i < data.getData().length; i++) {

            T obj = (T) new Object();

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
            if (checkDuplicate(obj) == false) {historyInterface.save(obj);}
        }
    }

    //saving the history data to DB
//    private void saveAllDataPerMinute(CryptoRoot data, String fsym, String tsym){
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
//
//    private void saveAllDataPerHour(CryptoRoot data, String fsym, String tsym){
//
//
//
//        //loop through the data object and set it to DB
//        for(int i = 0; i < data.getData().length; i++) {
//
//            HistoHour obj = new HistoHour();
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
//            if (checkDuplicate(obj) == false) {histoHourRepository.save(obj);}
//        }
//    }

    //checking if there is any duplicate time in the data based on fromCurrency, toCurrency, and time
    private <T extends History> boolean checkDuplicate (T obj){
        T t = (T) historyInterface.findByTimeAndFromCurrencyAndToCurrency(obj.getTime(), obj.getFromCurrency(), obj.getToCurrency());
        if (t == null) {
            return false;
        }else return true;
    }

//    //checking if there is any duplicate time in the data based on fromCurrency, toCurrency, and time
//    private boolean checkDuplicate (HistoHour obj){
//        HistoHour histoHour = (HistoHour) histoHourRepository.findByTimeAndFromCurrencyAndToCurrency(obj.getTime(), obj.getFromCurrency(), obj.getToCurrency());
//        if (histoHour == null) {
//            return false;
//        }else return true;
//    }
//
//    private boolean checkDuplicate (History obj){
//        HistoDay histoDay = histoDayRepository.findByTimeAndFromCurrencyAndToCurrency(obj.getTime(), obj.getFromCurrency(), obj.getToCurrency());
//        if (histoDay == null) {
//            return false;
//        }else return true;
//    }

//    public ArrayList<History> getAllData(){
//
//        return (ArrayList<History>) histoMinuteRepository.findAll();
//    }
//
//    public ArrayList<History> getDataByFsym(String fromCurrency){
//
//        return histoMinuteRepository.findByFromCurrency(fromCurrency);
//    }
//
//    public ArrayList<History> getDataByTsym(String tsym) {
//        return histoMinuteRepository.findByToCurrency(tsym);
//    }
//
//    public History getDataById(int id) {
//        return histoMinuteRepository.findById(id);
//    }
//
//    public ArrayList<History> getDataByFsymAndTsym(String fsym, String tsym){
//        return histoMinuteRepository.findByFromCurrencyAndToCurrency(fsym, tsym);
//    }
//
//    public String addData(History data) {
//        histoMinuteRepository.save(data);
//        return "Data inserted";
//    }
//
//    public String deleteDataById(int id) {
//        histoMinuteRepository.delete(id);
//        return "Data id: " + id + " deleted.";
//    }
//
//    public History update(History data) {
//        histoMinuteRepository.save(data);
//        return histoMinuteRepository.findById(data.getId());
//    }
//

}
