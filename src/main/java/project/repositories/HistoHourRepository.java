package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.models.HistoHour;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Transactional
@Repository("histoHourRepository")
public interface HistoHourRepository extends HistoryInterface{

//    public HistoHour findByTimeAndFromCurrencyAndToCurrency(long time, String fromCurrency, String toCurrency);
//    public ArrayList<HistoHour> findByFromCurrency(String fsym);
//    public ArrayList<HistoHour> findByToCurrency(String tsym);
//    public ArrayList<HistoHour> findByFromCurrencyAndToCurrency(String fsym, String tsym);
//    public HistoHour findById(int id);
}
