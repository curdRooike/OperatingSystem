package experiment1;

/**
 * @author 古丶野
 * @version 1.0
 * @project OperatingSystem
 * @description 时间片轮转调度算法
 * @date 2022/10/27 19:13:05
 */
public interface ProcessSchedulingAlgorithm {
    void round_cal();
    PCB get_process_round();
    int process_finish();
    void display_round();
    void set_state();
    void cpu_round(PCB q);// 采用时间片轮转调度算法执行某一进程
    PCB get_next(PCB k, PCB head);
}
