package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.models.History;

import java.util.ArrayList;
import java.util.Date;

@Repository
public interface HistoryInterface extends JpaRepository<History, Integer>{

    public History findByTimeAndFromCurrencyAndToCurrencyAndTimesignalAndDate(long time, String fromCurrency, String toCurrency, String timesignal, Date date);
    public ArrayList<History> findByFromCurrency(String fsym);
    public ArrayList<History> findByToCurrency(String tsym);
    public ArrayList<History> findByFromCurrencyAndToCurrency(String fsym, String tsym);
    public History findById(int id);

}
