package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.models.History;

import java.util.ArrayList;
import java.util.Date;

@Repository
public interface HistoryInterface extends JpaRepository<History, Integer>{

    public History findByTimeAndFromCurrencyAndToCurrencyAndTimesignal(long time, String fromCurrency, String toCurrency, String timesignal);
    public ArrayList<History> findByFromCurrencyAndTimesignal(String fsym, String timesignal);
    public ArrayList<History> findByToCurrencyAndTimesignal(String tsym, String timesignal);
    public ArrayList<History> findByFromCurrencyAndToCurrencyAndTimesignal(String fsym, String tsym, String timesignal);
    public History findByTimeAndTimesignal(long time, String timesignal);
    public History findById(int id);

}
