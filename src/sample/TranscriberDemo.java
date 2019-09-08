package sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

public class TranscriberDemo {

    public static void main(String[] args) throws Exception {

        Configuration configuration = new Configuration();

        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
        LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);

        recognizer.startRecognition(true);
        while(true) {
            SpeechResult result = recognizer.getResult();
            System.out.println(result.getHypothesis());
            if(result.getHypothesis().contains("stop")){
                break;
            }

        }

        recognizer.stopRecognition();
    }
}