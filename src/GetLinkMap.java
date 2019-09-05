import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class GetLinkMap extends RecursiveTask<String>
{
    private final Node node; // узел

    GetLinkMap(Node node) {
        this.node = node;
    }

    @Override
    protected String compute() {
        StringBuffer map = new StringBuffer(node.getValue());
        System.out.println(map.toString());// чтобы не скучно было ждать конец программы

        List<GetLinkMap> subTasks = new LinkedList<>();
        try {
            List<Node> getChildren = node.getChildren();
            if (getChildren != null)
            {
                for (Node child : getChildren) {
                    GetLinkMap task = new GetLinkMap(child);
                    task.fork(); // запустим асинхронно
                    subTasks.add(task);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(GetLinkMap task : subTasks)
        {
            String taskAnswer = task.join();
            String byPass = node.getLevel();
            map.append(byPass).append(taskAnswer); // дождёмся выполнения задачи и добавим результат
        }
        return map.toString();
    }
}
