package br.com.letscode.java;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Aplicacao {

    private List<Oscar> oscares;
    private List<Oscar> oscaresHomens;
    private List<Oscar> oscaresMulheres;

    public static void main(String[] args) {
        Aplicacao app = new Aplicacao();
        app.leituraArquivo1();
        app.leituraArquivo2();
        app.oscares = app.leituraArquivos();
        app.premiacaoMaisJovem();
        app.atrizMaisPremiada();
        app.atrizMaisPremiadaEntre20e30();
        app.maisDeUmOscar();
        app.consultaAtor("Norma Shearer");
    }

    private void consultaAtor(String name) {
        oscares.stream()
                .filter(p -> p.getName().equals(name))
                .collect(Collectors.toSet())
                .forEach(System.out::println);
    }

    private void premiacaoMaisJovem(){
        this.oscaresHomens.stream()
            .min(Comparator.comparingInt(Oscar::getAge))
            .ifPresent(System.out::println);
    }

    private void atrizMaisPremiada() {
        this.oscaresMulheres.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(atriz -> System.out.println(atriz.getKey().getName()));
    }

    private void atrizMaisPremiadaEntre20e30() {
        this.oscaresMulheres.stream()
                .filter(atriz -> atriz.getAge() >= 20)
                .filter(atriz -> atriz.getAge() <= 30)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(atriz -> System.out.println(atriz.getKey().getName()));
    }

    private void maisDeUmOscar() {
        this.oscares.stream()
                .filter(n -> this.oscares.stream()
                        .filter(x -> x.getName().equals(n.getName()))
                        .count() > 1)
                .collect(Collectors.toSet())
                .forEach(p -> System.out.println(p.getName()));
    }

    private List<Oscar> leituraArquivos() {
        /*List<Oscar> stream1 = this.oscaresHomens;
        List<Oscar> stream2 = this.oscaresMulheres;

        List<Oscar> resultingStream = Stream.concat(stream1, stream2);

        assertEquals(
                Arrays.asList(1, 3, 5, 2, 4, 6),
                resultingStream.collect(Collectors.toList()));*/
        List<Oscar> oscares = new ArrayList<>();
        oscares.addAll(this.oscaresMulheres);
        oscares.addAll(this.oscaresHomens);
        return oscares;
    }

    private void leituraArquivo1() {
        String filePath = getFilePathResourceAsString("OscarMulheres.csv");
        try (Stream<String> lines = Files.lines(Path.of(filePath))) {
            this.oscaresMulheres = lines.skip(1)
                    .map(Oscar::fromLine)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void leituraArquivo2() {
        String filePath = getFilePathResourceAsString("OscarHomens.csv");
        try (Stream<String> lines = Files.lines(Path.of(filePath))) {
            this.oscaresHomens = lines.skip(1)
                    .map(Oscar::fromLine)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFilePathResourceAsString(String fileName) {
        URL url = getClass().getClassLoader().getResource(fileName);
        File file = new File(Objects.requireNonNull(url).getFile());
        return file.getPath();
    }
}
