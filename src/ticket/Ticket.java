package ticket;

import sms.Smsc_api;

import java.text.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.text.MaskFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class Ticket {

    public class DateLabelFormatter extends AbstractFormatter {

        private String datePattern = "dd.MM.yyyy";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }

    public Ticket() {
        JFormattedTextField telephone=null;

        JTextField fromField = new JTextField();
        JTextField toField = new JTextField();
       /* JTextField dateField = new JTextField();
        JTextField startTimeField = new JTextField();
        JTextField endTimeField = new JTextField();
        JTextField typeField = new JTextField();*/

        String[] sss = {"\u041F", "\u041A", "\u041B","\u04211", "\u04212"};
        JComboBox typebox = new JComboBox(sss);

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel;
        datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        JSpinner timeSpinnerStart = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditorStart = new JSpinner.DateEditor(timeSpinnerStart, "HH:mm");
        timeSpinnerStart.setEditor(timeEditorStart);
        timeSpinnerStart.setValue(new Date()); // will only show the current time

        JSpinner timeSpinnerEnd = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditorEnd = new JSpinner.DateEditor(timeSpinnerEnd, "HH:mm");
        timeSpinnerEnd.setEditor(timeEditorEnd);
        timeSpinnerEnd.setValue(new Date()); // will only show the current time

        try {
            MaskFormatter formatter=new MaskFormatter("'+'3'8'0#########");
            telephone = new JFormattedTextField(formatter);
            telephone.setValue("+380666627720");
        } catch (ParseException ex) {
            Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
        }

        Object[] message = {
                "\u041E\u0442\u043A\u0443\u0434\u0430:", fromField,
                "\u041A\u0443\u0434\u0430:", toField,
                "\u0414\u0430\u0442\u0430 \u0432 \u0444\u043E\u0440\u043C\u0430\u0442\u0435 \u0414\u0414.\u041C\u041C.\u0413\u0413\u0413\u0413:", datePicker,
                "\u0412\u0440\u0435\u043C\u044F \u043E\u0442\u043F\u0440\u0430\u0432\u043B\u0435\u043D\u0438\u044F \u0432 \u0444\u043E\u0440\u043C\u0430\u0442\u0435 \u0427\u0427:\u041C\u041C", timeSpinnerStart,
                "\u0412\u0440\u0435\u043C\u044F \u043F\u0440\u0438\u0431\u044B\u0442\u0438\u044F \u0432 \u0444\u043E\u0440\u043C\u0430\u0442\u0435 \u0427\u0427:\u041C\u041C", timeSpinnerEnd,
                "\u0422\u0438\u043F \u043C\u0435\u0441\u0442\u0430: \u041B - \u041B\u042E\u041A\u0421; \u041A - \u041A\u0423\u041F\u0415; \u041F - \u041F\u041B\u0410\u0426\u041A\u0410\u0420\u0422", typebox,
                "tel:",telephone
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Ticket", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            from = fromField.getText();
            to = toField.getText();

            Date selectedDate = (Date) datePicker.getModel().getValue();
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            date = df.format(selectedDate);


            DateFormat dfHHmm = new SimpleDateFormat("HH:mm");
            startTime = dfHHmm.format(timeSpinnerStart.getValue());
            endTime = dfHHmm.format(timeSpinnerEnd.getValue());
            type = typebox.getSelectedItem().toString();

            tel = telephone.getText();
        } else {
            System.out.println("CANCELED");
        }

        baseUrl = "http://booking.uz.gov.ua/ru/";
        fullType = "";
//        from = JOptionPane.showInputDialog("\u041E\u0442\u043A\u0443\u0434\u0430:");
//        to = JOptionPane.showInputDialog("\u041A\u0443\u0434\u0430:");
//        date = JOptionPane.showInputDialog("\u0414\u0430\u0442\u0430 \u0432 \u0444\u043E\u0440\u043C\u0430\u0442\u0435 \u0414\u0414.\u041C\u041C.\u0413\u0413\u0413\u0413:");
//        startTime = JOptionPane.showInputDialog("\u0412\u0440\u0435\u043C\u044F \u043E\u0442\u043F\u0440\u0430\u0432\u043B\u0435\u043D\u0438\u044F \u0432 \u0444\u043E\u0440\u043C\u0430\u0442\u0435 \u0427\u0427:\u041C\u041C");
//        endTime = JOptionPane.showInputDialog("\u0412\u0440\u0435\u043C\u044F \u043F\u0440\u0438\u0431\u044B\u0442\u0438\u044F \u0432 \u0444\u043E\u0440\u043C\u0430\u0442\u0435 \u0427\u0427:\u041C\u041C");
//        type = JOptionPane.showInputDialog("\u0422\u0438\u043F \u043C\u0435\u0441\u0442\u0430: \u041B - \u041B\u042E\u041A\u0421; \u041A - \u041A\u0423\u041F\u0415; \u041F - \u041F\u041B\u0410\u0426\u041A\u0410\u0420\u0422");

    }

    public void openBrowser() throws Exception {
        if (type.contains("\u041B")) {
            fullType = "\u041B\u042E\u041A\u0421";
        } else if (type.contains("\u041A")) {
            fullType = "\u041A\u0423\u041F\u0415";
        } else {
            fullType = "\u041F\u041B\u0410\u0426\u041A\u0410\u0420\u0422";
        }
        JOptionPane.showMessageDialog(null, (new StringBuilder("\u0412\u044B \u0445\u043E\u0442\u0438\u0442\u0435 \u043D\u0430\u0439\u0442\u0438 "))
                .append(fullType).append(" \u0431\u0438\u043B\u0435\u0442 \u0438\u0437 ")
                .append(from).append(" \u0434\u043E ")
                .append(to)
                .append(" \u043D\u0430 ")
                .append(date)
                .append(" \u0447\u0438\u0441\u043B\u043E \u0432 ")
                .append(startTime).toString());

        driver = new FirefoxDriver();
        driver.get(baseUrl);
        driver.findElement(By.name("station_from")).sendKeys(new CharSequence[]{from});
        Thread.sleep(500L);
        try {
            Robot robot = new Robot();
            robot.keyPress(40);
            robot.keyRelease(10);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        driver.findElement(By.name("station_till")).sendKeys(new CharSequence[]{to});
        Thread.sleep(500L);

        try {
            Robot robot = new Robot();
            robot.keyPress(40);
            robot.keyRelease(10);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        driver.findElement(By.id("date_dep")).clear();

        driver.findElement(By.id("date_dep")).sendKeys(new CharSequence[]{date});
        Thread.sleep(1000L);
        try {
            Robot robot = new Robot();
            robot.keyRelease(10);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        Thread.sleep(1000L);
        mainLoop:
        do {
            driver.findElement(By.name("search")).click();
            Thread.sleep(1000L);
            List allTrains = driver.findElements(By.cssSelector(".vToolsDataTableRow"));
            for (Iterator iterator = allTrains.iterator(); iterator.hasNext();) {
                WebElement train = (WebElement) iterator.next();
                System.out.println(train.findElement(By.xpath(".//*[@class='time']")).getText());
                if (train.findElement(By.xpath(".//*[@class='time']")).getText().contains(startTime) && train.findElement(By.xpath(".//*[@class='time']")).getText().contains(endTime) && train.findElement(By.xpath(".//*[@class='place']")).getText().contains(type)) {
                    JOptionPane.showMessageDialog(null, (new StringBuilder("\u0423\u0420\u0410! \u0415\u0441\u0442\u044C ")).append(fullType).append(" \u043D\u0430 ").append(startTime).toString());
                    sendSMS();
                    break mainLoop;
                }
            }
            List allTrains1 = driver.findElements(By.cssSelector(".vToolsDataTableRow2"));
            for (Iterator iterator1 = allTrains1.iterator(); iterator1.hasNext();) {
                WebElement train1 = (WebElement) iterator1.next();
                System.out.println(train1.findElement(By.xpath(".//*[@class='time']")).getText());
                if (train1.findElement(By.xpath(".//*[@class='time']")).getText().contains(startTime) && train1.findElement(By.xpath(".//*[@class='time']")).getText().contains(endTime) && train1.findElement(By.xpath(".//*[@class='place']")).getText().contains(type)) {
                    JOptionPane.showMessageDialog(null, (new StringBuilder("\u0423\u0420\u0410! \u0415\u0441\u0442\u044C ")).append(fullType).append(" \u043D\u0430 ").append(startTime).toString());
                    sendSMS();
                    break mainLoop;
                }
            }
            Thread.sleep(30000L);
        } while (true);
    }

    private void sendSMS(){
        Smsc_api smsSender = new Smsc_api();
        //smsSender.send_sms(tel, "Your ticket has found", 1, "", "", 0, "", "");
    }

    public static void main(String args[]) throws Exception {
        Ticket ticket = new Ticket();
        ticket.openBrowser();
    }

    private WebDriver driver;
    private String baseUrl;
    String from;
    String to;

    String date;
    String startTime;
    String endTime;
    String type;
    String fullType;
    String tel;
}
