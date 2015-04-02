package edu.pku.emotion.feat;

/**
 * 
 * @author intfloat@pku.edu.cn
 * @author labyrinth@pku.edu.cn
 *
 */
public class Feature {
    
    //  String description for a feature
    private String desc;
    private double value;
    private int index;
    
    /**
     * 
     * @param desc
     * 
     * suitable for categorical features such as bag of words,
     * default value is 1.0
     */
    public Feature(String desc) {
        this.setDesc(desc);
        this.setValue(1.0);
        this.setIndex(FeatureMap.getIndex(this));
    }
    
    /**
     * 
     * @param desc
     * @param value
     * 
     * suitable for numerical features such as length.
     */
    public Feature(String desc, double value) {
        this.setDesc(desc);
        this.setValue(value);
        this.setIndex(FeatureMap.getIndex(this));     
    }
    
    @Override
    public String toString() {
        return this.index + " " + this.desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
//      avoid string splitting ambiguity
        desc = desc.replace(' ', '_');
        desc = desc.replace('\t', '_');
        this.desc = desc;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    } 
}
