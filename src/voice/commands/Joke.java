package voice.commands;

import Core.AlertDialog;
import Core.VoiceCommand;

import javax.swing.*;
import java.util.Random;

public class Joke implements VoiceCommand {

    private int state = 0;

    @Override
    public void execute() {
        state = 1 + (int)(Math.random() * ((20 -1) +1));
        randomJoke();
    }

    @Override
    public void close() {
    }

    private void randomJoke(){
       switch (state){
           case 1:
               JOptionPane.showMessageDialog(null,"Did you hear the one about the guy with the broken hearing aid? Neither did he","Joke",JOptionPane.INFORMATION_MESSAGE);
               break;
           case 2:
               JOptionPane.showMessageDialog(null,"What do you call a fly without wings? A walk.","Joke",JOptionPane.INFORMATION_MESSAGE);
               break;
           case 3:
               JOptionPane.showMessageDialog(null,"When my wife told me to stop impersonating a flamingo, I had to put my foot down.","Joke",JOptionPane.INFORMATION_MESSAGE);
               break;
           case 4:
               JOptionPane.showMessageDialog(null,"What do you call someone with no nose? Nobody knows.","Joke",JOptionPane.INFORMATION_MESSAGE);
               break;
           case 5:
               JOptionPane.showMessageDialog(null,"What time did the man go to the dentist? Tooth hurt-y.","Joke",JOptionPane.INFORMATION_MESSAGE);
               break;
           case 6:
               JOptionPane.showMessageDialog(null,"Why can’t you hear a pterodactyl go to the bathroom? The p is silent.","Joke",JOptionPane.INFORMATION_MESSAGE);
               break;
           case 7:
               JOptionPane.showMessageDialog(null,"How many optometrists does it take to change a light bulb? 1 or 2? 1... or 2?","Joke",JOptionPane.INFORMATION_MESSAGE);
               break;
           case 8:
               JOptionPane.showMessageDialog(null,"I was thinking about moving to Moscow but there is no point Russian into things.","Joke",JOptionPane.INFORMATION_MESSAGE);
               break;
           case 9:
               JOptionPane.showMessageDialog(null,"Why does Waldo only wear stripes? Because he doesn't want to be spotted.","Joke",JOptionPane.INFORMATION_MESSAGE);
               break;
           case 10:
               JOptionPane.showMessageDialog(null,"Do you know where you can get chicken broth in bulk? The stock market.","Joke",JOptionPane.INFORMATION_MESSAGE);
               break;
           case 11:
               JOptionPane.showMessageDialog(null,"I used to work for a soft drink can crusher. It was soda pressing.","Joke",JOptionPane.INFORMATION_MESSAGE);
               break;
           case 12:
               JOptionPane.showMessageDialog(null,"A ghost walks into a bar and asks for a glass of vodka but the bar tender says, “sorry we don’t serve spirits”","Joke",JOptionPane.INFORMATION_MESSAGE);
               break;
           case 13:
               JOptionPane.showMessageDialog(null,"I went to the zoo the other day, there was only one dog in it. It was a shitzu.","Joke",JOptionPane.INFORMATION_MESSAGE);
               break;
           case 14:
               JOptionPane.showMessageDialog(null,"I gave all my dead batteries away today, free of charge.","Joke",JOptionPane.INFORMATION_MESSAGE);
               break;
           case 15:
               JOptionPane.showMessageDialog(null,"Why are skeletons so calm? Because nothing gets under their skin.","Joke",JOptionPane.INFORMATION_MESSAGE);
               break;
           case 16:
               JOptionPane.showMessageDialog(null,"There’s a new type of broom out, it’s sweeping the nation.","Joke",JOptionPane.INFORMATION_MESSAGE);
               break;
           case 17:
               JOptionPane.showMessageDialog(null,"Why don’t seagulls fly over the bay? Because then they’d be bay-gulls!","Joke",JOptionPane.INFORMATION_MESSAGE);
               break;
           case 18:
               JOptionPane.showMessageDialog(null,"What did celery say when he broke up with his girlfriend? She wasn't right for me, so I really don't carrot all.","Joke",JOptionPane.INFORMATION_MESSAGE);
               break;
           case 19:
               JOptionPane.showMessageDialog(null,"Q: What’s 50 Cent’s name in Zimbabwe? A: 400 Million Dollars.","Joke",JOptionPane.INFORMATION_MESSAGE);
               break;
           case 20:
               JOptionPane.showMessageDialog(null,"What's the worst thing about ancient history class? The teachers tend to Babylon.","Joke",JOptionPane.INFORMATION_MESSAGE);
               break;

       }
    }
}
