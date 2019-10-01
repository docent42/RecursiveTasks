import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.*;

public class Root implements Node
{
    private String link;// ссылка на узел
    private String level;// уровень отступа
    private int depth;// уровень погружения в дереве ссылок
    private TreeSet<String> linkSet; // библиотека уникальных ссылок

    Root(String link, String level, int depth, TreeSet<String> linkSet)
    {
       this.link = link;
       this.level = level;
       this.depth = depth;
       this.linkSet = linkSet;
    }
    @Override
    public List<Node> getChildren() throws Exception
    {
        Thread.sleep(500);// защита от блока сайта
        Document doc = Jsoup.connect(link).ignoreContentType(true).maxBodySize(0).get();
        Elements links = doc.select("a[href^=/]");

        TreeSet<String> tempLinks = new TreeSet<>();
        for (Element link : links){
            if (!(linkSet.contains(link.absUrl("href").split("#",2)[0])))// добавляем в библиотеку ссылки
            {
                linkSet.add(link.absUrl("href")); // общая библиотека ссылок
                tempLinks.add(link.absUrl("href"));// ссылки на детей узла
            }
        }
        if (links.size() == 0) return null;

        List<Node> collection = new ArrayList<>();
        for (String link : tempLinks) {
            collection.add(new Root(link, level + "\t",++depth,linkSet));
        }
        return collection;
    }
    @Override
    public String getValue() {
        return link + "\n";
    }
    @Override
    public String getLevel() {
        return level;
    }
}
