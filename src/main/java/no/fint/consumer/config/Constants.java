
package no.fint.consumer.config;

public enum Constants {
;

    public static final String COMPONENT = "felles-kodeverk";
    public static final String COMPONENT_CONSUMER = COMPONENT + " consumer";
    public static final String CACHE_SERVICE = "CACHE_SERVICE";

    
    public static final String CACHE_INITIALDELAY_FYLKE = "${fint.consumer.cache.initialDelay.fylke:60000}";
    public static final String CACHE_FIXEDRATE_FYLKE = "${fint.consumer.cache.fixedRate.fylke:900000}";
    
    public static final String CACHE_INITIALDELAY_KJONN = "${fint.consumer.cache.initialDelay.kjonn:70000}";
    public static final String CACHE_FIXEDRATE_KJONN = "${fint.consumer.cache.fixedRate.kjonn:900000}";
    
    public static final String CACHE_INITIALDELAY_LANDKODE = "${fint.consumer.cache.initialDelay.landkode:80000}";
    public static final String CACHE_FIXEDRATE_LANDKODE = "${fint.consumer.cache.fixedRate.landkode:900000}";
    
    public static final String CACHE_INITIALDELAY_SPRAK = "${fint.consumer.cache.initialDelay.sprak:90000}";
    public static final String CACHE_FIXEDRATE_SPRAK = "${fint.consumer.cache.fixedRate.sprak:900000}";
    
    public static final String CACHE_INITIALDELAY_KOMMUNE = "${fint.consumer.cache.initialDelay.kommune:100000}";
    public static final String CACHE_FIXEDRATE_KOMMUNE = "${fint.consumer.cache.fixedRate.kommune:900000}";
    

}
