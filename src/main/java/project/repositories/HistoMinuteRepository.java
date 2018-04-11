package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.models.HistoMinute;

import java.util.ArrayList;

@Repository("histoMinuteRepository")
public interface HistoMinuteRepository extends JpaRepository<HistoMinute, Integer>{

    public HistoMinute findByTimeAndFromCurrencyAndToCurrency(long time, String fromCurrency, String toCurrency);
    public ArrayList<HistoMinute> findByFromCurrency(String fsym);
    public ArrayList<HistoMinute> findByToCurrency(String tsym);
    public ArrayList<HistoMinute> findByFromCurrencyAndToCurrency(String fsym, String tsym);
    public HistoMinute findById(int id);
}
