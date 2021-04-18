import model.Map;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Map.saveToPNG(new Map(80));
    }
}
