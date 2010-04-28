package de.cosmocode.palava.util.qa;

/**
 * Specifies the different modes all quality assurance filters
 * have to support.
 *
 * @author Willi Schoenborn
 */
public enum QualityMode {

    /**
     * Quality assurance filters log a warning but do not fail
     * when running in this mode.
     */
    WARN, 
    
    /**
     * Quality assurance filters throw an appropriate exception
     * when running in this mode.
     */
    FAIL;
    
}
