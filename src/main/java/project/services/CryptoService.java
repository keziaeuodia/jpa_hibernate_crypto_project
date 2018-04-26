package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import project.exceptions.CryptoDataNotFoundException;
import project.exceptions.DuplicateDataException;
import project.models.*;
import project.repositories.HistoryInterface;

import java.util.ArrayList;

@Service
public class CryptoService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    HistoryInterface historyInterface;

    /**
     * Method called by the controller to make a third party API call to crypto compare
     * @param param histoday, histohour, or histominute
     * @param fsym fromCurrency passed by user
     * @param tsym toCurrency passed by user
     * @param limit limit of the data taken, max 2000
     * @param persist if persist is true, saveAllData method will be called to persist data to the database
     * @return
     */
    public CryptoRoot search(String param, String fsym, String tsym, int limit, boolean persist) throws DuplicateDataException {
        String fquery = "https://min-api.cryptocompare.com/data/" + param + "?fsym="+fsym+"&tsym="+tsym+"&limit="+limit;

        //mapping the data to the class
        CryptoRoot data = restTemplate.getForObject(fquery, CryptoRoot.class);

        //persisting data to DB
        if (persist){
            saveAllData(data, fsym, tsym, param);
        }
        return data;
    }


    /**
     * Method that's being called by the search method when persist value is true
     * This method calls the checkDuplicate method that will persist data to the database, only if there's no duplicate
     * @param data data retrieved from the search result
     * @param fsym fromCurrency
     * @param tsym toCurrency
     * @param param histominute, histohour, or histoday
     */
    private void saveAllData(CryptoRoot data, String fsym, String tsym, String param) throws DuplicateDataException{
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
            obj.setDate(data.getData()[i].getTime());
            switch (param){
                case "histominute":
                    obj.setTimesignal("minute");
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


    /**
     * checking if there is any duplicate time in the data based on time, fromCurrency, toCurrency, and timesignal
     * @param obj is an History object which will be compared against the data in the database
     * @return boolean value true if the data doesn't exist in the database
     */
    private boolean checkDuplicate (History obj){
        History history =  historyInterface.findByTimeAndFromCurrencyAndToCurrencyAndTimesignal(
                obj.getTime(), obj.getFromCurrency(), obj.getToCurrency(), obj.getTimesignal());
        if (history == null) {
            return false;
        }else return true;
    }


    public ArrayList<History> getAllData(){

        return (ArrayList<History>) historyInterface.findAll();
    }

    public ArrayList<History> getDataByFsym(String fromCurrency, String timesignal){
        return historyInterface.findByFromCurrencyAndTimesignal(fromCurrency, timesignal);
    }

    public ArrayList<History> getDataByTsym(String tsym, String timesignal) {
        return historyInterface.findByToCurrencyAndTimesignal(tsym, timesignal);
    }

    public History getDataById(int id) {
        return historyInterface.findById(id);
    }

    public ArrayList<History> getDataByFsymAndTsym(String fsym, String tsym, String timesignal){
        return historyInterface.findByFromCurrencyAndToCurrencyAndTimesignal(fsym, tsym, timesignal);
    }

    public String addData(History data) {
        historyInterface.save(data);
        return "Data inserted";
    }

    public String deleteDataById(int id) throws CryptoDataNotFoundException{
        if (historyInterface.findById(id) != null) {
            historyInterface.delete(id);
            return "Data id: " + id + " deleted.";
        } else throw new CryptoDataNotFoundException("The data you are looking for does not exist");
    }

    public History update(History data) throws CryptoDataNotFoundException{
        if (historyInterface.findById(data.getId()) != null) {
            historyInterface.save(data);
            return historyInterface.findById(data.getId());
        } else throw new CryptoDataNotFoundException("The data you are trying to update does not exist");
    }

    public History findByTime(long time, String timesignal){
        return historyInterface.findByTimeAndTimesignal(time, timesignal);
    }

}
