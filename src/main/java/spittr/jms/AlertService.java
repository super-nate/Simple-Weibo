package spittr.jms;

import spittr.entity.Spittle;

/**
 * Created by Administrator on 2016/8/6.
 */

public interface AlertService {
    String sendSpittleAlert(Spittle spittle);
}
