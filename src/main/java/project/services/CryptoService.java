package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import project.models.*;
import project.repositories.HistoryInterface;

import java.util.ArrayList;

@Service
public class CryptoService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    HistoryInterface historyInterface;


    public CryptoRoot search(String param, String fsym, String tsym, int limit, boolean persist) {
        String fquery = "https://min-api.cryptocompare.com/data/" + param + "?fsym="+fsym+"&tsym="+tsym+"&limit="+limit;

        //mapping the data to the class
        CryptoRoot data = restTemplate.getForObject(fquery, CryptoRoot.class);

        //persisting data to DB
        if (persist){
            saveAllData(data, fsym, tsym, param);
        }
        return data;
    }


    private void saveAllData(CryptoRoot data, String fsym, String tsym, String param) {
        for(int i = 0; i < data.getData().length; i++) {

            History obj = new History();

            obj.setFromCurrency(fsym);
            obj.setToCurrency(tsym);
            obj.setTime(data.getData()[i].getTime());
            obj.setClose(data.getData()[i].getClose());
            obj.setHigh(data.getData()[i].getHigh());
            obj.setLow(data.getData()[i].getLow());
            obj.setOpen(data.getData()[i].getOpen());
            obj.setVolumefrom(data.getData()[i].getVolumefrom());
            obj.setVolumeto(data.getData()[i].getVolumeto());
            switch (param){
                case "histominute":
                    obj.setTimesignal("min");
                    break;
                case "histohour":
                    obj.setTimesignal("hour");
                    break;
                case "histoday":
                    obj.setTimesignal("day");
            }
            //calling the method that checks duplicate
            if (checkDuplicate(obj) == false) {historyInterface.save(obj);}
        }
    }


    //checking if there is any duplicate time in the data based on fromCurrency, toCurrency, and time
    private boolean checkDuplicate (History obj){
        History history =  historyInterface.findByTimeAndFromCurrencyAndToCurrency(obj.getTime(), obj.getFromCurrency(), obj.getToCurrency());
        if (history == null) {
            return false;
        }else return true;
    }


    public ArrayList<History> getAllData(){

        return (ArrayList<History>) historyInterface.findAll();
    }

    public ArrayList<History> getDataByFsym(String fromCurrency){

        return historyInterface.findByFromCurrency(fromCurrency);
    }

    public ArrayList<History> getDataByTsym(String tsym) {
        return historyInterface.findByToCurrency(tsym);
    }

    public History getDataById(int id) {
        return historyInterface.findById(id);
    }

    public ArrayList<History> getDataByFsymAndTsym(String fsym, String tsym){
        return historyInterface.findByFromCurrencyAndToCurrency(fsym, tsym);
    }

    public String addData(History data) {
        historyInterface.save(data);
        return "Data inserted";
    }

    public String deleteDataById(int id) {
        historyInterface.delete(id);
        return "Data id: " + id + " deleted.";
    }

    public History update(History data) {
        historyInterface.save(data);
        return historyInterface.findById(data.getId());
    }


}
