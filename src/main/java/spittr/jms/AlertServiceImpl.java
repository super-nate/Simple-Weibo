package spittr.jms;

/**
 * Created by Administrator on 2016/8/6.
 */

import org.springframework.stereotype.Component;
import spittr.entity.Spittle;


//@Component("alertService")
public class AlertServiceImpl implements AlertService {

    public String sendSpittleAlert(final Spittle spittle) {
        System.out.println(spittle.getMessage());
        return "success";
    }
}
