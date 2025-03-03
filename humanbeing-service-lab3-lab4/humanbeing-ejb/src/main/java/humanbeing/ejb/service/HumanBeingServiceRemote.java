package humanbeing.ejb.service;

import humanbeing.ejb.dto.PageHumanBeing;
import humanbeing.ejb.model.HumanBeing;
import jakarta.ejb.Local;
import jakarta.ejb.Remote;

import java.util.List;

@Local
public interface HumanBeingServiceRemote {
    HumanBeing createHumanBeing(HumanBeing humanBeing);
    PageHumanBeing getHumanBeings(int limit, int offset, List<String> sort, List<String> filter);
    HumanBeing getHumanBeingById(int id);
    void deleteHumanBeing(int id);
    HumanBeing updateHumanBeing(HumanBeing humanBeing);
    double calculateImpactSpeedSum();
    int countHumanBeingsBySoundtrackNameLessThan(String soundtrackName);
}
