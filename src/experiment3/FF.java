package experiment3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author 古丶野
 * @version 1.0
 * @project OperatingSystem
 * @description 首次适应算法
 * @date 2022/12/3 02:02:47
 */
public class FF {
    private static class Node {
        int id;    // 作业id,为-1代表空闲分区;大于0代表已分配
        int start; // 初始地址
        int size;  // 大小

        public String toString() {
            return String.format("[%4d, %4d, %4d]", id, start, size);
        }
    }
    private static final int SIZE = 4; // 定义碎片大小
    private static final Scanner sc = new Scanner(System.in);
    // 返回分区链表
    private static List<Node> init() {
        List<Node> list = new ArrayList<>();
        Node node = new Node();
        // 初始化,为整个内存空间分配一个空闲节点
        node.id = -1;
        node.start = 0;
        System.out.print("请输入内存空间大小: ");
        node.size = sc.nextInt();
        list.add(node);

        return list;
    }
    // 回收作业id的内存,并合并相邻的空闲分区
    private static boolean del(List<Node> list, int id) {
        Node p = null;
        int i;

        // 找到作业id所在的节点p,i为其下标
        for (i = 0; i < list.size(); i++) {
            p = list.get(i);
            if (p.id == id) break;
        }
        if (i == list.size()) return false;//此作业id不存在

        p.id = -1; // 回收分区

        Node a, b;
        if (i != 0) { // 若第i-1个节点和第i个节点相邻,合并两个分区
            a = list.get(i - 1);
            b = list.get(i);
            if (a.id == -1 && b.id == -1 && a.start + a.size == b.start) {
                a.size += b.size;
                list.remove(i);
                i--;
            }
            // i--是因为可能存在合并后的节点可能与后一个节点相邻
        }
        if (i != list.size() - 1) { // 若第i个节点和第i+1个节点相邻,合并两个分区
            a = list.get(i);
            b = list.get(i + 1);
            if (a.id == -1 && b.id == -1 && a.start + a.size == b.start) {
                a.size += b.size;
                list.remove(i + 1);
            }
        }

        return true;
    }

    // 为作业id在分区链表list中分配大小为size的内存
    private static boolean add(List<Node> list, int id, int size) {
        Node p = null;
        int i;

        // 找到第一个未分配且大于size的内存空间节点p,i为其下标
        for (i = 0; i < list.size(); i++) {
            p = list.get(i);
            if (p.id == -1 && p.size >= size)
                break;
        }

        if (i == list.size()) return false; // 不存在未分配且大于或等于size的内存空间节点，分配失败

        // 当原来节点的大小大于size+SIZE时需要创建一个新节点temp保留余下的分区,并插在p的后面
        if (p.size - size > SIZE) {
            Node temp = new Node();
            temp.id = -1;
            temp.start = p.start + size;
            temp.size = p.size - size;
            list.add(i + 1, temp);
        }
        // 将原来节点变成已分配的节点,当剩余空间大于SIZE时,该节点大小为size;当剩余空间小于或等于SIZE时,该节点大小不变，因为剩余大小已经不够再次分配任务;
        p.id = id;
        p.size = (p.size - size > SIZE ? size : p.size);

        return true;
    }

    private static void show(List<Node> list) {
        System.out.println("已分配分区:");
        int i = 1;
        for (Node temp : list) {
            if (temp.id != -1) System.out.println("分区号:" + i + " 分配情况:" + temp);
            i++;
        }
        i = 1;
        System.out.println("未分配分区:");
        for (Node temp : list) {
            if (temp.id == -1) System.out.println("分区号:" + i + " 分配情况:" + temp);
            i++;
        }
//        System.out.println(list);
    }
    public static void main(String[] args) {
        List<Node> list = init();
        int id, size, op;

        while (true) {
            System.out.println("\n************************************************");
            System.out.println("   1: 为新作业分配内存        2: 撤销作业释放内存");
            System.out.println("   3: 查看FF算法内存分配      4: 退出");
            System.out.print("请输入操作: ");
            op = sc.nextInt();
            switch (op) {
                case 1:
                    System.out.print("请输入作业id和作业大小size: ");
                    id = sc.nextInt();
                    size = sc.nextInt();
                    if (add(list, id, size)) System.out.println("分配成功");
                    else System.out.println("分配失败");
                    break;
                case 2:
                    System.out.print("请输入需要撤销的作业id: ");
                    id = sc.nextInt();
                    if (del(list, id)) System.out.println("撤销成功");
                    else System.out.println("撤销失败，此作业id不存在");
                    break;
                case 3:
                    show(list);
                    break;
                case 4:
                    return;
            }
        }
    }
}
