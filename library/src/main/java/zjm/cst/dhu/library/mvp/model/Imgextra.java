package zjm.cst.dhu.library.mvp.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zjm on 2017/5/10.
 */


public class Imgextra {

    private String imgsrc;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
