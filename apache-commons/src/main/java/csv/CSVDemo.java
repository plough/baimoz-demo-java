package csv;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class CSVDemo {
  private static final String[] FILE_HEADER = {"ID", "Name", "Gender", "Major"};
  private static final String FILE_NAME = "student.csv";

  public void writeCSV() {
    Student stuTZY = new Student("001", "谭振宇", "男", "GIS");
    Student stuZJL = new Student("002", "周杰伦", "男", "音乐");
    List<Student> students = new ArrayList<>();
    students.add(stuTZY);
    students.add(stuZJL);

    // 这里显式地配置一下CSV文件的Header，然后设置跳过Header（要不然读的时候会把头也当成一条记录）
    CSVFormat format =
        CSVFormat.DEFAULT.builder().setHeader(FILE_HEADER).setSkipHeaderRecord(true).build();

    // 这是写入CSV的代码
    try (Writer out = new FileWriter(FILE_NAME);
        CSVPrinter printer = new CSVPrinter(out, format)) {
      for (Student student : students) {
        List<String> records = new ArrayList<>();
        records.add(student.getID());
        records.add(student.getName());
        records.add(student.getGender());
        records.add(student.getMajor());
        printer.printRecord(records);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void readCSV() {
    CSVFormat format =
        CSVFormat.DEFAULT.builder().setHeader(FILE_HEADER).setSkipHeaderRecord(false).build();

    // 这是从上面写入的文件中读出数据的代码
    try (Reader in = new FileReader(FILE_NAME)) {
      Iterable<CSVRecord> records = format.parse(in);
      String strID;
      String strName;
      for (CSVRecord record : records) {
        // 既可使用列名，又可使用序号
        strID = record.get("ID");
        strName = record.get(1);
        System.out.println(strID + " " + strName);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] arg) {
    CSVDemo csvDemo = new CSVDemo();
    csvDemo.writeCSV();
    csvDemo.readCSV();
  }
}
