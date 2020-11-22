package model.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import utils.DateTimeHelper;
import utils.Period;

public class Cours {
    private String  idClasses;
    private Integer IdPlace;
    private Period period;
    private Integer realDuration;

    @JsonIgnore
    private Integer idCours;

    public Cours()
    {
    }

    public Cours(Period period, String idClasses, Integer place, Integer realDuration)
    {
        this.period = period;
        this.idClasses = idClasses;
        this.IdPlace = place;
        this.realDuration = realDuration;
    }

    public Period getPeriod()
    {
        return period;
    }

    public void setPeriod(Period period)
    {
        this.period = period;
    }

    public String getIdClasses()
    {
        return idClasses;
    }

    public void setIdClasses(String idClasses)
    {
        this.idClasses = idClasses;
    }

    public Integer getIdPlace()
    {
        return IdPlace;
    }

    public void setIdPlace(Integer idPlace)
    {
        this.IdPlace = idPlace;
    }

    public Integer getRealDuration()
    {
        return realDuration;
    }

    public Integer getWorkingDayDuration()
    {
        Integer workingHour = DateTimeHelper.toHourBetweenDateWithoutHolydays(period);

        return workingHour > realDuration ? realDuration : workingHour;
    }

    public void setRealDuration(Integer realDuration)
    {
        this.realDuration = realDuration;
    }

    public Integer getIdCours() {
        return idCours;
    }

    public void setIdCours(Integer idCours) {
        this.idCours = idCours;
    }
}
