package cf.yuanbing.uidemo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

@SuppressWarnings("unchecked")
public class MyJson {
    private ArrayList<Object> collections = new ArrayList<>();
    public HashMap file = null;
    private Object location = null;
    private ArrayList<String> tracks = new ArrayList<>();

    // 读取出文件中的内容，将文件中的内容存储到容器中
    public boolean load(InputStreamReader input) {
        String line = null;
        int lineNumber = 0;
        try {
            BufferedReader br = new BufferedReader(input);
            while ((line = br.readLine()) != null) {
                if (lineNumber > 0) {
                    analyzeLine(line);
                    lineNumber++;
                } else {
                    collections.add(new HashMap<String, Object>());
                    lineNumber++;
                }
            }
            br.close();
            file = (HashMap) collections.get(collections.size() - 1);
        } catch (IOException | IndexOutOfBoundsException | NumberFormatException | ClassCastException anyException) {
            return false;
        }
        return true;
    }

    // 从第二行开始，判断出这一行应该创建的对象和应该存储的值
    private void analyzeLine(String line) throws IndexOutOfBoundsException {
        if ("{".equals(line.split(" ")[line.split(" ").length - 1]) &&
                !"},".equals(line.split(" ")[line.split(" ").length - 2])) {
            collections.add(new HashMap<String, Object>());
            addElements(line, collections.get(collections.size() - 1), 2);
        } else if (line.contains("[\"") && line.contains("\"]")) {
            collections.add(new ArrayList<Object>());
            for (String element : line.split("\\[")[1].split("]")[0].split(",")) {
                ((ArrayList) collections.get(collections.size() - 1)).add(element.split("\"")[1]);
            }
            ((HashMap) collections.get(collections.size() - 2)).put(line.split("\"")[1], collections.get(collections.size() - 1));
            collections.remove(collections.size() - 1);
        } else if ("[".equals(line.split(" ")[line.split(" ").length - 1]) &&
                !"],".equals(line.split(" ")[line.split(" ").length - 2])) {
            collections.add(new ArrayList<Object>());
            addElements(line, collections.get(collections.size() - 1), 2);
        } else if (line.contains("{}")) {
            addElements(line, new HashMap<String, Object>(), 1);
        } else if (line.contains("[]")) {
            addElements(line, new ArrayList<Object>(), 1);
        } else if ("}".equals(line.split(" ")[line.split(" ").length - 1]) ||
                "]".equals(line.split(" ")[line.split(" ").length - 1]) ||
                "},".equals(line.split(" ")[line.split(" ").length - 1]) ||
                "],".equals(line.split(" ")[line.split(" ").length - 1])) {
            if (collections.size() > 1) {
                collections.remove(collections.size() - 1);
            }
        } else if (line.contains("[{")) {
            collections.add(new ArrayList<Object>());
            addElements(line, collections.get(collections.size() - 1), 2);
            collections.add(new HashMap<String, Object>());
            ((ArrayList) collections.get(collections.size() - 2)).add(collections.get(collections.size() - 1));
        } else if (line.contains("}, {")) {
            collections.remove(collections.size() - 1);
            collections.add(new HashMap<String, Object>());
            ((ArrayList) collections.get(collections.size() - 2)).add(collections.get(collections.size() - 1));
        } else if (line.contains("}]")) {
            collections.remove(collections.size() - 1);
            collections.remove(collections.size() - 1);
        } else {
            if (line.contains(":")) {
                ((HashMap)collections.get(collections.size() - 1)).put(line.split("\"")[1], analyzeValue(line));
            } else {
                ((ArrayList) collections.get(collections.size() - 1)).add(line.split("\"")[1]);
            }
        }
    }

    // 对不同的容器和不同的值以不同的方式存储
    private void addElements(String line, Object value, int delta) {
        try {
            ((HashMap)collections.get(collections.size() - delta)).put(line.split("\"")[1], value);
        } catch (ClassCastException classCastException) {
            ((ArrayList)collections.get(collections.size() - delta)).add(value);
        }
    }

    // 对每一行需要添加的键值对对应的值进行处理
    private Object analyzeValue(String line) {
        String value = line.split(": ")[1];
        if (value.contains(",")) {
            value = value.split(",")[0];
        }
        if ("\"\"".equals(value)) {
            return "";
        } else if (value.contains("\"")) {
            return value.split("\"")[1].replace("\\n", "\n");
        } else {
            if ("false".equals(value)) {
                return false;
            } else if ("true".equals(value)) {
                return true;
            } else if ("null".equals(value)) {
                return null;
            } else {
                try {
                    return Long.parseLong(value);
                } catch (NumberFormatException numberFormatException) {
                    return Double.parseDouble(value);
                }
            }
        }
    }

    // 在命令行中详细查看文件
    public void showInfo() {
        location = file;
        ArrayList<Object> history = new ArrayList<>();
        history.add(file);
        tracks.add("file");
        printDetail();
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("输入冒号前的名称查看详细信息(帮助:/help):");
            for (Iterator<String> it = tracks.iterator(); it.hasNext(); ) {
                System.out.print(it.next() + "->");
            }
            System.out.print(":");
            String aim = scan.next();
            if (aim.contains("/")) {
                if ("/exit".equals(aim)) {
                    break;
                } else if ("/home".equals(aim)) {
                    location = history.get(0);
                    printDetail();
                    history.clear();
                    history.add(file);
                    tracks.clear();
                    tracks.add("file");
                } else if ("/back".equals(aim)) {
                    if (tracks.size() > 1) {
                        history.remove(history.size() - 1);
                        location = history.get(history.size() - 1);
                        printDetail();
                        tracks.remove(tracks.size() - 1);
                    } else {
                        printDetail();
                        System.out.println("无法再回退");
                    }
                } else if ("/help".equals(aim)) {
                    printDetail();
                    System.out.println("回到起始位置:/home\n返回上一级:/back\n退出:/exit");
                } else {
                    printDetail();
                    System.out.println("未找到命令" + ":" + aim);
                }
            } else {
                try {
                    int index = Integer.parseInt(aim);
                    location = ((ArrayList) location).get(index);
                    tracks.add(aim);
                    printDetail();
                    history.add(location);
                } catch (NumberFormatException numberFormatException) {
                    try {
                        location = ((HashMap) location).get(aim);
                        tracks.add(aim);
                        printDetail();
                        history.add(location);
                    } catch (NullPointerException nullPointException) {
                        tracks.remove(aim);
                        location = history.get(history.size() - 1);
                        printDetail();
                        System.out.println("未找到" + ":" + aim);
                    } catch (ClassCastException classCastException) {
                        printDetail();
                        System.out.println("未找到" + ":" + aim);
                    }
                } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    tracks.remove(aim);
                    location = history.get(history.size() - 1);
                    printDetail();
                    System.out.println("未找到" + ":" + aim);
                }
            }
        }
    }

    // 打印出容器内存储的信息
    private void printDetail() {
        System.out.println("=============================================");
        try {
            for (Iterator<String> it = ((HashMap) location).keySet().iterator(); it.hasNext(); ) {
                String key = it.next();
                System.out.println(key + ":" + ((HashMap) location).get(key));
            }
        } catch (ClassCastException outerClassCastException) {
            int index = 0;
            try {
                for (Iterator<Object> it = ((ArrayList) location).iterator(); it.hasNext(); ) {
                    System.out.println(index + ":" + it.next());
                    index++;
                }
            } catch (ClassCastException innerClassCastException) {
                System.out.println(location);
            }
        }
        System.out.println("=============================================");
    }
}
