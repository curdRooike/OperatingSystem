package experiment1;

/**
 * @author 古丶野
 * @version 1.0
 * @project OperatingSystem
 * @description PCB实体类
 * @date 2022/10/21 11:23:39
 */
public class PCB implements Cloneable{
    enum state{
        ready,
        execute,
        block,
        finish
    };// 定义进程状态
    private String name;//进程名
    private int cpuTime;//CPU 运行时间
    private int needTime;//运行所需的时间
    private int count;//执行次数
    private int round;//时间片轮转轮次
    private state process;//进程状态
    private PCB next;

    public Object clone()  {
        PCB person = null;
        try {
            person = (PCB) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCpuTime() {
        return cpuTime;
    }

    public void setCpuTime(int cpuTime) {
        this.cpuTime = cpuTime;
    }

    public int getNeedTime() {
        return needTime;
    }

    public void setNeedTime(int needTime) {
        this.needTime = needTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setRound(int round) {
        this.round = round;
    }
    public int getRound(){
        return round;
    }

    public void setProcess(state process) {
        this.process = process;
    }
    public state getProcess(){
        return process;
    }

    @Override
    public String toString() {
        return "PCB{" +
                "name='" + name + '\'' +
                ", cpuTime=" + cpuTime +
                ", needTime=" + needTime +
                ", count=" + count +
                ", round=" + round +
                ", process=" + process +
                ", next=" + next +
                '}';
    }

    public void setNext(PCB p){
        if(p!=null)next = (PCB) p.clone();
        else next = null;
    }
    public PCB getNext(){
        return next;
    }
}
