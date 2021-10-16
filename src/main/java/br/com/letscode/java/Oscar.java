package br.com.letscode.java;

import lombok.Value;

import static java.lang.Integer.parseInt;

@Value
public class Oscar {

    Integer year;
    Integer age;
    String name;
    String movie;

    public static Oscar fromLine(String line) {
        String[] split = line.split("; ");
        return new Oscar(
                parseInt(split[1]),
                parseInt(split[2]),
                split[3],
                split[4]
        );
    }

    public String getName() {
        return name;
    }
}
