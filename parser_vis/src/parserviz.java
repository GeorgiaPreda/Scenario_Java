import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class parserviz {

    public void parse_room() {
        try {
            String room;
            String furniture;
            File file = new File("E:\\Year2Coursework\\scenario2\\vis.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append("\n");
            }
            fileReader.close();
            // System.out.println("Contents of file:");
            // System.out.println(stringBuffer.toString());
            String[] lines = stringBuffer.toString().split("\\n");
            for (String s : lines) {
                String parts_room[] = s.split(";");
                for (String f : parts_room) {
                    String part[] = f.split(",");
                    for(String f1:part)
                    {if (f1.charAt(0) == '(' || f1.charAt(1) == '(') {
                        String overlook_parant[] = f1.split("\\(");
                        System.out.print(overlook_parant[1] + " ");
                    }

                    if (f1.charAt(f1.length()-1) == ')' || f1.charAt(f1.length() - 2) == ')') {
                        String overlook_parant[] = f1.split("\\)");
                        System.out.println(overlook_parant[0] + " ");
                    }}
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        parserviz nou=new parserviz();
        nou.parse_room();
    }
}
