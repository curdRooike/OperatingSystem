package experiment1;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author 古丶野
 * @version 1.0
 * @project OperatingSystem
 * @description 进程调度实验
 * @date 2022/10/21 10:50:04
 */
public class ProcessScheduling implements ProcessSchedulingAlgorithm,Cloneable{
    public static final int P_NUM = 5;
    public static final int P_TIME = 50;
    public static List PCBLinkedList = new LinkedList<PCB>();
    public Scanner sc = new Scanner(System.in);
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    public static void main(String[] args) {
        ProcessScheduling processScheduling = new ProcessScheduling();
        processScheduling.round_cal();
    }

    @Override
    public void round_cal() {
        PCB p,r;
        int i = 0;
        p = get_process_round();
        System.out.println(PCBLinkedList);
        int cpu = 0;
        r = (PCB) p.clone();
        while (process_finish() == 0){
            cpu += 2;
            cpu_round(r);
            r = (PCB) get_next(r, p).clone();
            System.out.println(i+++":"+r);
            System.out.println("cpu  " + cpu);
            display_round();
            set_state();
        }
    }

    @Override
    public PCB get_process_round() {
        PCB q = new PCB();
        PCB t = new PCB();
        PCB p = new PCB();
        int i = 0;
        System.out.println("input name and time");
        while(i < P_NUM){
            q.setName(sc.next());
            q.setNeedTime(sc.nextInt());
            q.setCpuTime(0);
            q.setRound(0);
            q.setCount(0);
            q.setProcess(PCB.state.ready);
            q.setNext(null);
            if (i == 0) {
                p = (PCB) q.clone();
            }else {
                t.setNext(q);
            }
            t = (PCB) q.clone();
            PCBLinkedList.add(t);
            i++;
        }
        return p;
    }

    @Override
    public int process_finish() {
        int b = 1,i = 0;
        while (b !=0 && i < PCBLinkedList.size()){
            PCB q = (PCB) PCBLinkedList.get(i);
            if (q.getNeedTime() == 0 )
                b = 1;
            else b = 0;
            i++;
        }
        return b;
    }

    @Override
    public void display_round() {
        System.out.println("NAME         CPUTIME         NEEDTIME         COUNT         ROUND         STATE");
        for(int i=0;i<PCBLinkedList.size();i++){
            PCB p = (PCB) PCBLinkedList.get(i);
            System.out.print(p.getName() + "             ");
            System.out.print(p.getCpuTime() + "               ");
            System.out.print(p.getNeedTime() + "               ");
            System.out.print(p.getCount() + "              ");
            System.out.print(p.getRound() + "           ");
            switch (p.getProcess()){
                case ready:
                    System.out.println("ready");
                    break;
                case execute:
                    System.out.println("execute");
                    break;
                case finish:
                    System.out.println("finish");
                    break;
            }
        }
    }

    @Override
    public void set_state() {
        for (int i = 0; i < PCBLinkedList.size(); i++) {
            PCB p = (PCB) PCBLinkedList.get(i);
            if (p.getNeedTime() == 0)
                p.setProcess(PCB.state.finish);
            if (p.getProcess() == PCB.state.execute)
                p.setProcess(PCB.state.ready);
        }
    }

    @Override
    public void cpu_round(PCB q) {
        q.setCpuTime(q.getCpuTime() + 2);
        q.setNeedTime(q.getNeedTime() - 2);
        if (q.getNeedTime() < 0)
            q.setNeedTime(0);
        q.setCount(q.getCount() + 1);//q.count++;
        q.setRound(q.getRound() + 1);//q.round++;
        q.setProcess(PCB.state.execute);//q.process = state.execute;
        System.out.println("q:"+q);
        for (int i = 0; i < PCBLinkedList.size(); i++) {
            PCB temp = (PCB) PCBLinkedList.get(i);
            if (q.getName() == temp.getName()) {
                PCBLinkedList.remove(i);
                PCBLinkedList.add(i,q);
            }
        }
    }

    @Override
    public PCB get_next(PCB k, PCB head) {
        System.out.println(k.getName() + " " + head.getName() + "------");
        PCB t;
        t = (PCB) k.clone();//t = k;
        int opsition = 0;
        for(int i=0;i<PCBLinkedList.size();i++) {//从列表中查找k的位置
            PCB temp = (PCB) PCBLinkedList.get(i);
            if(t.getName() == temp.getName()) {
                opsition = i;
                break;
            }
        }
        System.out.println(t+":"+opsition);
        do {
            //t = t.getNext();//t = t.next;
            if(opsition+1 < PCBLinkedList.size()) {
                t = (PCB) PCBLinkedList.get(++opsition);
                //System.out.println(t.getName() +"-----------");
                //break;
            }
            else{
                opsition++;
                break;
            }
        }while(t != null && t.getProcess() == PCB.state.finish && opsition < PCBLinkedList.size());
        System.out.println(t+":"+opsition);
        if(opsition == PCBLinkedList.size()) {
            t = (PCB) head.clone();//t = head;
            //System.out.println(t.getName() + " -------------");
            //while (t.getNext() != k && t.getProcess() == state.finish) t = t.getNext();
            for(int i = 0; i < PCBLinkedList.size(); i++){
                PCB temp = (PCB) PCBLinkedList.get(i);
                if(temp.getName() == t.getName()){
                    opsition = i;
                    break;
                }
            }
            System.out.println(t+":"+opsition);
            do {
                if(opsition+1 < PCBLinkedList.size()) t = (PCB) PCBLinkedList.get(++opsition);
                //opsition++;
            }while(t != null && t.getNext() != k && t.getProcess() == PCB.state.finish && opsition < PCBLinkedList.size());
        }
        System.out.println(t+":"+opsition);
        return t;
    }
}
