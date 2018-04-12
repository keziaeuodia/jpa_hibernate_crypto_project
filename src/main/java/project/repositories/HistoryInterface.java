package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import project.models.History;

import javax.persistence.MappedSuperclass;
import java.util.ArrayList;

@NoRepositoryBean
public interface HistoryInterface extends JpaRepository<History, Integer>{

    public History findByTimeAndFromCurrencyAndToCurrency(long time, String fromCurrency, String toCurrency);
    public ArrayList<History> findByFromCurrency(String fsym);
    public ArrayList<History> findByToCurrency(String tsym);
    public ArrayList<History> findByFromCurrencyAndToCurrency(String fsym, String tsym);
    public History findById(int id);

}
