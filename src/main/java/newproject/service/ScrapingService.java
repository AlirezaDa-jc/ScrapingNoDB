package newproject.service;

import newproject.CassandraConnector;
import newproject.domain.Problems;
import newproject.domain.ProblemSet;
import newproject.repository.KeyspaceRepository;
import newproject.repository.ProblemRepository;
import newproject.repository.ProblemsRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created By Alireza Dolatabadi
 * Date: 7/4/2022
 * Time: 9:37 AM
 */
@Service
public class ScrapingService {

    private final CassandraConnector cassandraConnector;
    private final KeyspaceRepository keyspaceRepository;
    private final ProblemsRepository problemsRepository;
    private final ProblemRepository problemRepository;

    public ScrapingService(CassandraConnector cassandraConnector, KeyspaceRepository keyspaceRepository, ProblemsRepository problemsRepository, ProblemRepository problemRepository) {
        this.cassandraConnector = cassandraConnector;
        this.keyspaceRepository = keyspaceRepository;
        this.problemsRepository = problemsRepository;
        this.problemRepository = problemRepository;
    }

    public  List<Problems> getCodeChet() throws IOException {
        keyspaceRepository.useKeyspace("problems");
        List<Problems> problems = new ArrayList<>();
        Document doc = Jsoup.connect("https://codeforces.com/problemset").get();
        problemsRepository.createTable();
        List<Element> tr = doc.getElementsByTag("tr").stream().filter(c -> c instanceof Element).map(c -> (Element) c).filter(c -> c.childNodeSize() > 0).collect(Collectors.toList());
        tr.subList(1,tr.size()).forEach(c -> {
            Problems problems1 = new Problems();
            try {
                List<Element> collect = c.children().stream().filter(x -> x.childNodeSize() > 0).collect(Collectors.toList());
                String num = collect.get(0).child(0).childNode(0).toString().trim();
                problems1.setNum(num);
                String name = collect.get(1).child(1).child(1).childNode(0).toString().trim();
                problems1.setName(name);
                List<String> list = new ArrayList<>();
                collect.get(1).child(1).children().stream().filter(x -> x instanceof Element).forEach(x -> list.add(x.childNode(0).toString()));
                problems1.setTags(list);
                String rating = collect.get(3).child(0).childNode(0).toString();
                problems1.setRating(rating);
                problemsRepository.insertProblem(problems1);
                problems.add(problems1);
            }catch (Exception ignored){
                ignored.printStackTrace();
                System.err.println(ignored.getMessage());
            }
        });

        return problems;
    }

    public List<ProblemSet> getLeetCode() throws IOException {
        keyspaceRepository.useKeyspace("problems");
        List<ProblemSet> problems = new ArrayList<>();
        Document doc = Jsoup.connect("https://leetcode.com/problemset/algorithms/").get();
        problemRepository.createTable();
        Elements probs = doc.getElementsByClass("odd:bg-layer-1 even:bg-overlay-1 dark:odd:bg-dark-layer-bg dark:even:bg-dark-fill-4");
        for (Element elements : probs) {
            Element element = elements.child(1);
            Element name = element.child(0).child(0).child(0).child(0).child(0);
            String num = name.childNode(0).toString();
            String title = name.childNode(4).toString();
            String url = element.child(0).child(0).child(0).child(0).child(0).attr("href");
            String acceptance = elements.child(3).childNode(0).childNode(0).toString();
            String difficulty = elements.child(4).childNode(0).childNode(0).toString();
            ProblemSet problem = ProblemSet.builder().title(title).acceptance(acceptance)
                    .difficulty(difficulty).num(num).url(url).build();
            problemRepository.insertbook(problem);
            problems.add(problem);
        }
        return problems;
    }

    public  void createKeySpace(){
        cassandraConnector.connect();
        keyspaceRepository.createKeyspace("problems" , "SimpleStrategy" , 1);
    }

}
