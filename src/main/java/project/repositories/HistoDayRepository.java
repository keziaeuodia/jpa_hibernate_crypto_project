package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.models.HistoDay;
import project.models.History;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Transactional
@Repository
public interface HistoDayRepository extends HistoryInterface{
//
//    public HistoDay findByTimeAndFromCurrencyAndToCurrency(long time, String fromCurrency, String toCurrency);
//    public ArrayList<HistoDay> findByFromCurrency(String fsym);
//    public ArrayList<HistoDay> findByToCurrency(String tsym);
//    public ArrayList<HistoDay> findByFromCurrencyAndToCurrency(String fsym, String tsym);
//    public HistoDay findById(int id);

}
