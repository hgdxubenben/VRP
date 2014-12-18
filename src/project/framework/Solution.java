// Copyright (c) 2012 Thomas Weise (http://www.it-weise.de/, tweise@gmx.de)
// GNU LESSER GENERAL PUBLIC LICENSE (Version 3, 29 June 2007)

package project.framework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

/**
 * The solution class holds a complete solution to a scenario. the list of tasks: This list contains
 * order ids. An order id must always occur twice: the first time for pick up, the second time for
 * delivery. A car will then step-by-step process the list. CHANGING THIS CLASS IS NOT ALLOWED.
 * 
 * @author Thomas Weise
 */
public final class Solution {

  /** the schedules */
  int[][] m_schedules;

  /** create an empty solution */
  public Solution() {
    super();
    this.m_schedules = Scenario.DUMMY_I2D;
  }

  /**
   * Create a new solution based on a set of car schedules
   * 
   * @param schedules
   *          the car schedules
   */
  public Solution(final int[][] schedules) {
    super();

    int[] x;
    int i;

    if ((schedules != null) && ((i = schedules.length) > 0)) {
      this.m_schedules = schedules;
      for (; (--i) >= 0;) {
        x = schedules[i];
        if ((x == null) || (x.length <= 0)) {
          schedules[i] = Scenario.DUMMY_I1D;
        }
      }
    } else {
      this.m_schedules = Scenario.DUMMY_I2D;
    }
  }

  /**
   * Create a solution that only involves the first car and not any other
   * 
   * @param schedule
   *          the schedule for the first car
   */
  public Solution(final int[] schedule) {
    super();

    final int[][] data;

    if ((schedule != null) && (schedule.length > 0)) {
      data = new int[][] {
        schedule };
    } else {
      data = Scenario.DUMMY_I2D;
    }
    this.m_schedules = data;
  }

  /**
   * Get the number of cars that this schedule uses.
   * 
   * @return the number of cars that this schedule uses
   */
  public final int carCount() {
    return this.m_schedules.length;
  }

  /**
   * Get the number of tasks for the given car
   * 
   * @param carIndex
   *          the index of the car
   * @return the number of tasks for the given car
   */
  public final int carTaskCount(final int carIndex) {
    return (((this.m_schedules.length > carIndex) && (this.m_schedules[carIndex] != null)) ? this.m_schedules[carIndex].length
        : 0);
  }

  /**
   * Get a specific task for a given car.
   * 
   * @param carIndex
   *          the car index
   * @param taskIndex
   *          the task index
   * @return the task
   */
  public final int carTask(final int carIndex, final int taskIndex) {
    return this.m_schedules[carIndex][taskIndex];
  }

  /**
   * Get a copy of the data
   * 
   * @return the copied data
   */
  public final int[][] copyData() {
    int[][] c;
    int i;

    c = this.m_schedules.clone();
    for (i = c.length; (--i) >= 0;) {
      if (c[i] != Scenario.DUMMY_I1D) {
        c[i] = c[i].clone();
      }
    }

    return c;
  }

  public final void moveData(int sx, int sy, int dx,int dy) {
   int temp = this.m_schedules[dx][dy];
   this.m_schedules[dx][dy] = this.m_schedules[sx][sy];
   this.m_schedules[sx][sy] = temp;
  }
  /**
   * Print the solution to a print stream
   * 
   * @param console
   *          the stream to print to
   */
  public final void print(final PrintStream console) {
    int[][] data;
    int[] sched;
    int lenI, lenJ, i, j;
    boolean empty;

    console.print(Scenario.COMMENT);
    console.println(//
        " For a discussion on the file format and meanings see the end of the file."); //$NON-NLS-1$
    console.println();

    console.print(Scenario.COMMENT);
    console.println(//
        " -------------- SOLUTION DATA --------------"); //$NON-NLS-1$

    console.print(Scenario.COMMENT);
    console.println(//
        " Car ID: Order1, Order2, Order3, ..."); //$NON-NLS-1$
    console.println();
    data = this.m_schedules;
    empty = true;

    if (data != null) {
      lenI = data.length;
      if (lenI > 0) {

        for (i = 0; i < lenI; i++) {
          sched = data[i];
          if (sched != null) {
            lenJ = sched.length;
            if (lenJ > 0) {
              console.print(i);
              console.print(':');
              for (j = 0; j < lenJ; j++) {
                console.print(' ');
                console.print(sched[j]);
              }
              console.println();
              empty = false;
            }
          }
        }

      }

    }

    if (empty) {
      console.print(Scenario.COMMENT);
      console.println(//
          "This solution is empty!");//$NON-NLS-1$
    }

    console.println();
    console.print(Scenario.COMMENT);
    console.println(//
        " A solution conaints schedules for one or multiple cars."); //$NON-NLS-1$

    console.print(Scenario.COMMENT);
    console.println(//
        " A car schedule is a list of order IDs and it tells the cars what to do."); //$NON-NLS-1$

    console.println();

    console.print(Scenario.COMMENT);
    console.println(//
        " A car can do three things: drive to a location, pick up an order, and deliver/unload an order."); //$NON-NLS-1$

    console.print(Scenario.COMMENT);
    console.println(//
        " An order ID can occur two times in a car schedule:"); //$NON-NLS-1$

    console.print(Scenario.COMMENT);
    console.println(//
        "  1. The first time an order ID occurs, the car drives to the order's pick-up location (if it is not already there) and picks up the order."); //$NON-NLS-1$

    console.print(Scenario.COMMENT);
    console.println(//
        "  2. The second time an order ID occurs, the car drives to the order's delivery location (if it is not already there) and unloads/delivers the order."); //$NON-NLS-1$

    console.print(Scenario.COMMENT);
    console.println(//
        " Of course, a car can load multiple orders at the same time, if its capacity allows for this."); //$NON-NLS-1$

    console.print(Scenario.COMMENT);
    console.println(//
        " If a car drives somewhere, it will take all the orders it has loaded along with it."); //$NON-NLS-1$

    console.println(Scenario.COMMENT);

    console.print(Scenario.COMMENT);
    console.println(//
        " Example. 1: 3 4 4 5 3 5"); //$NON-NLS-1$
    console.print(Scenario.COMMENT);
    console.println(//
        " Meaning: Car 1 drives to the pick-up location of order 3, picks the order up, the travels to the pick-up location of order 4 and loads it as well. It will then drive to the delivery location of order 4 and unload it. Next it travels to the pick-up location of order 5, loads that order and then drives to the delivery location of order 3 and delivers order 3. Finally, it will drive to the delivery location of order 5 and deliver order 5."); //$NON-NLS-1$
    console.print(Scenario.COMMENT);
    console.println();

    console.print(Scenario.COMMENT);
    console.println(//
        " If an order ID occurs more than two times in one car schedule or the order has already been delivered or loaded by another car, its occurence will be ignored."); //$NON-NLS-1$
    console.print(Scenario.COMMENT);
    console.println(//
        " If an order ID occurs only one time in a car schedule, this means that the order would be loaded into the car but never unloaded and never delivered."); //$NON-NLS-1$

    console.print(Scenario.COMMENT);
    console.println(" Orders occuring not in the schedule are ignored."); //$NON-NLS-1$

    console.print(Scenario.COMMENT);
    console.print(" There is a penalty of "); //$NON-NLS-1$
    console.print(Scenario.UNDELIVERED_ORDER_PENALTY);
    console.println(" per order which was picked-up but not delivered."); //$NON-NLS-1$

    console.print(Scenario.COMMENT);
    console.print(//
        " If the vehicle's capacity is exceeded, this will also result in a penalty of "); //$NON-NLS-1$
    console.print(Scenario.PHYSICAL_VIOLATION_PENALTY);
    console.println(" per violation."); //$NON-NLS-1$

    console.println();
    console.print(Scenario.COMMENT);
    console.println(//
        " The goal is to find solutions that allow our 'company' to correctly complete its work as quickly as possible."); //$NON-NLS-1$

    console.print(Scenario.COMMENT);
    console.println(//
        " This means that all orders should be delivered and all cars must be back in the depot."); //$NON-NLS-1$

    console.print(Scenario.COMMENT);
    console.println(//
        " Any problem can be solved by using single car, but of course, by using more cars we can finish the work faster."); //$NON-NLS-1$
  }

  /**
   * write to a file
   * 
   * @param f
   *          the file
   * @throws IOException
   *           if something goes wrong
   */
  public final void writeFile(final File f) throws IOException {
    try (FileOutputStream fos = new FileOutputStream(f)) {
      try (PrintStream pos = new PrintStream(fos)) {
        this.print(pos);
      }
    }
  }

  /**
   * Read a solution from a file.
   * 
   * @param f
   *          the file
   * @throws IOException
   *           if io fails
   */
  public final void readFile(final File f) throws IOException {
    String s;
    String[] lst;
    int[][] tempList2D;
    int[] tempList1D;
    int c2d, c1d, i, idx;

    this.m_schedules = Scenario.DUMMY_I2D;

    tempList2D = new int[100][];
    c2d = 0;

    try (FileReader fr = new FileReader(f)) {
      try (BufferedReader br = new BufferedReader(fr)) {

        while ((s = br.readLine()) != null) {

          while ((i = s.indexOf(Scenario.COMMENT)) >= 0) {
            s = s.substring(0, i);
          }
          s = s.trim();
          if (s.length() <= 0) {
            continue;
          }

          i = s.indexOf(':');

          idx = Integer.parseInt(s.substring(0, i));
          s = s.substring(i + 1).trim();

          c1d = 0;
          if (s.length() > 0) {
            lst = Scenario.SPLIT_PATTERN.split(s);
            tempList1D = new int[lst.length];
            for (i = 0; i < lst.length; i++) {
              s = lst[i];
              if (s == null) {
                continue;
              }
              s = s.trim();
              if (s.length() > 0) {
                try {
                  tempList1D[c1d++] = Integer.parseInt(s);
                } catch (final Throwable tr) {
                  throw new IOException(tr);
                }
              }
            }

            if (c1d > 0) {
              if (idx >= tempList2D.length) {
                tempList2D = Arrays.copyOf(tempList2D, idx + 100);
              }

              if (c1d < tempList1D.length) {
                tempList2D[idx] = new int[c1d];
                System.arraycopy(tempList1D, 0, tempList2D[idx], 0, c1d);
              } else {
                tempList2D[idx] = tempList1D;
              }
              c2d = Math.max(c2d, idx + 1);
            }
          }
        }

      }
    }

    this.m_schedules = new int[c2d][];
    for (i = c2d; (--i) >= 0;) {
      this.m_schedules[i] = (((tempList2D[i] == null) || (tempList2D[i].length <= 0)) ? Scenario.DUMMY_I1D
          : tempList2D[i]);
    }
  }
}
