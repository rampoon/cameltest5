package se.rampoon.example5.utils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

    private final static String TIMESTAMP_PATTERN
            = "MM/dd/yyyy hh:mm a z";

    private final static DateTimeFormatter DATE_TIME_FORMATTER
            = DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN);

    /**
     * Pre Java8-version - NOT Threadsafe!
     * @param s
     * @return
     * @throws ParseException
     * @throws DatatypeConfigurationException
     */
    public XMLGregorianCalendar prejava8StringToXMLGregorianCalendar(String s)
            throws ParseException,
            DatatypeConfigurationException {
        XMLGregorianCalendar result = null;
        Date date;
        SimpleDateFormat simpleDateFormat;
        GregorianCalendar gregorianCalendar;

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        date = simpleDateFormat.parse(s);
        gregorianCalendar =
                (GregorianCalendar)GregorianCalendar.getInstance();
        gregorianCalendar.setTime(date);
        result = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        return result;
    }

    /**
     * Java8-version - Threadsafe
     * @param s
     * @return
     * @throws DatatypeConfigurationException
     */
    public static XMLGregorianCalendar stringToXMLGregorianCalendar(String s) throws DatatypeConfigurationException {
        GregorianCalendar calendar = new GregorianCalendar();

        DateTimeFormatter f = DateTimeFormatter.ofPattern("YYYY-MM-dd");
        LocalDate localDate = LocalDate.parse(s);
        calendar.clear();
        //assuming start of day
        calendar.set(localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth());

        XMLGregorianCalendar xgc = DatatypeFactory.newInstance()
                .newXMLGregorianCalendar(calendar);

        return xgc;
    }

    public static String xmlGregorianCalendarToString(XMLGregorianCalendar xgc) throws DatatypeConfigurationException {

        ZonedDateTime zdt = xgc.toGregorianCalendar().toZonedDateTime();
        System.out.println( DATE_TIME_FORMATTER.format(zdt) );

        ZonedDateTime zdtUTC = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        return DATE_TIME_FORMATTER.format(zdtUTC);
    }
}
