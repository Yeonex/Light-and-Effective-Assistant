package Core;

import java.io.*;


public class NameChanger {

    public NameChanger(String name) {
        if(name.toLowerCase().equals("lea")){
            return;
        }
        System.out.println(name);
        BufferedReader br = null;
        FileWriter writer = null;
      // File grammer = new File("D:\\Users\\ianza\\Documents\\lea\\src\\res\\grammar.gram");
        System.out.println(Notifications.class.getResource("/res/grammar.gram").getPath());
        File grammer = new File(Notifications.class.getResource("/res/grammar.gram").getPath());

        try {
            String oldContent ="";
            br = new BufferedReader(new FileReader(grammer));
            String line = br.readLine();
            while (line != null){
                oldContent = oldContent + line + System.lineSeparator();
                line = br.readLine();
            }

            if(!oldContent.contains("public <name> = (" + name + ")")){
                String newContent = oldContent.replaceAll("public <name> =.*;","public <name> = (" + name + ");");
                writer = new FileWriter(grammer);
                writer.write(newContent);
                writer.close();
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
