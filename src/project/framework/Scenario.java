package project.framework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Pattern;

import poad.IObjectiveFunction;

/**
 * This class holds all information of a problem instance.
 * 
 * @author Thomas Weise
 */
public final class Scenario implements IObjectiveFunction<Solution> {

  /** the depot location */
  public static final int DEPOT_LOCATION = 0;

  /** the penalty for physical violations */
  public static final long PHYSICAL_VIOLATION_PENALTY = (1000000000l);
  /** the penalty for a picked up order which has not been delivered */
  public static final long UNDELIVERED_ORDER_PENALTY = (1000000000l);
  /** empty penalty */
  public static final long EMPTY_PENALTY = (1000000000l);

  /** the location number string */
  public static final String LOCATION_NUMBER = "locationCount"; //$NON-NLS-1$
  /** the location list string */
  public static final String LOCATION_LIST = "locationList"; //$NON-NLS-1$
  /** the order number string */
  public static final String ORDER_NUMBER = "orderCount"; //$NON-NLS-1$
  /** order list string */
  public static final String ORDER_LIST = "orderList"; //$NON-NLS-1$
  /** the car number string */
  public static final String CAR_NUMBER = "carCount"; //$NON-NLS-1$
  /** the car list string */
  public static final String CAR_LIST = "carList"; //$NON-NLS-1$

  /** the comment string */
  public static final String COMMENT = "//"; //$NON-NLS-1$

  /** the location coordinates */
  private int[][] m_locations;

  /** the transportation orders */
  private int[][] m_orders;

  /** the cars */
  private int[] m_cars;

  /**
   * the internal distance matrix: not part of scenario, used for distance calculation
   */
  private transient int[][] m_distanceMatrix;

  /**
   * the order positions: not part of scenario, used for objective value calculation
   */
  private transient int[] m_orderPos;

  /** instantiate an empty scenario */
  public Scenario() {
    super();
    this.clear();
  }

  /** clear */
  private final void clear() {
    this.m_locations = Scenario.DUMMY_I2D;
    this.m_orders = Scenario.DUMMY_I2D;
    this.m_cars = Scenario.DUMMY_I1D;
    this.m_distanceMatrix = null;
  }

  /**
   * Get the number of locations. The depot is always location 0.
   * 
   * @return the number of locations
   */
  public final int locationCount() {
    return this.m_locations.length;
  }

  /**
   * Get the number of orders.
   * 
   * @return the number of orders
   */
  public final int orderCount() {
    return this.m_orders.length;
  }

  /**
   * Get the index of the pickup location of an order. The pickup location is where we need to go to
   * pick up the parcel. We will go there with a vehicle, pick it up, and transport it to the
   * delivery location.
   * 
   * @param index
   *          the order index
   * @return the pickup location of the order.
   */
  public final int orderPickupLocation(final int index) {
    return this.m_orders[index][Scenario.ORDER_PICKUP_LOCATION_INDEX];
  }

  /**
   * Get the index of the delivery location of an order. The delivery location is where we need to
   * take the order/parcel to. We will take the parcel from the pickup location with a vehicle and
   * then transport it to the delivery location.
   * 
   * @param index
   *          the order index
   * @return the delivery location of the order.
   */
  public final int orderDeliveryLocation(final int index) {
    return this.m_orders[index][Scenario.ORDER_DELIVERY_LOCATION_INDEX];
  }

  /**
   * Get the order's weight. This is how much space the order will take in a vehicle. A vehicle can
   * only pick up the order if it has at least this much unused space left inside. After picking up
   * an order, the free space in the vehicle is reduced by this amount.
   * 
   * @param index
   *          the order index
   * @return the weight of the order.
   */
  public final int orderWeight(final int index) {
    return this.m_orders[index][Scenario.ORDER_WEIGHT_INDEX];
  }

  /**
   * Get the amount of money we can earn by picking up the order.
   * 
   * @param index
   *          the order index
   * @return the weight of the order.
   */
  public final int orderProfit(final int index) {
    return this.m_orders[index][Scenario.ORDER_PROFIT_INDEX];
  }

  /**
   * Get the number of available cars.
   * 
   * @return the number of available cars
   */
  public final int carCount() {
    return this.m_cars.length;
  }

  /**
   * Get the capacity of a given car
   * 
   * @param index
   *          the index of the car
   * @return the car capacity
   */
  public final int carCapacity(final int index) {
    return this.m_cars[index];
  }

  /**
   * Get the cost to travel from one location to another one.
   * 
   * @param location1
   *          the first location
   * @param location2
   *          the second location
   * @return the distance between them
   */
  public final int travelCost(final int location1, final int location2) {
    final int loc1, loc2, nLoc;
    int d;
    final int[][] nodes;
    int[][] dm;
    int[] cl;
    final double x, y;

    if (location1 > location2) {
      loc1 = location1;
      loc2 = location2;
    } else {
      if (location1 >= location2) {
        return 0;
      }
      loc1 = location2;
      loc2 = location1;
    }

    nodes = this.m_locations;
    nLoc = nodes.length;
    dm = this.m_distanceMatrix;
    if (dm == null) {
      this.m_distanceMatrix = dm = new int[nLoc][];
    }

    cl = dm[loc1];
    if (cl == null) {
      dm[loc1] = cl = new int[loc1];
    }

    d = cl[loc2];
    if (d > 0) {
      return d;
    }

    x = (nodes[loc2][Scenario.LOCATION_X_INDEX] - nodes[loc1][Scenario.LOCATION_X_INDEX]);
    y = (nodes[loc2][Scenario.LOCATION_Y_INDEX] - nodes[loc1][Scenario.LOCATION_Y_INDEX]);
    cl[loc2] = d = ((int) (0.5d + Math.ceil(Math.sqrt((x * x) + (y * y)))));

    return d;
  }

  /**
   * Evaluate a given solution {@code s} and return its objective value.
   * 
   * @param s
   *          the solution to evaluate
   * @return the objective value
   */
  public final long netProfit(final Solution s) {
    return this.netProfit(s, null);
  }

  /**
   * Evaluate a given solution {@code s} and return its objective value. Returns negated profit.
   * 
   * @param s
   *          the solution to evaluate
   * @return the objective value
   */
  @Override
  public final double compute(final Solution s) {
    return (-this.netProfit(s));
  }

  /**
   * Evaluate a given solution {@code s} and return its objective value. Print debug information to
   * a console.
   * 
   * @param s
   *          the solution to evaluate
   * @param console
   *          the log console
   * @return the objective value
   */
  public final long netProfit(final Solution s, final PrintStream console) {
    final int[][] orders, schedule;
    final int[] cars;
    int[] orderPos, carTasks, order;
    final int carCount, maxCar;
    boolean hadCar;
    int curOrderPos, curCarTaskCount, curCar, curCarCapacity, curCarRemainingCapacity, curCarTaskIndex, orderID, curCarLoc, nextLoc, weight, orderInThisCar, curCost, profit;
    long totalProfit, carProfit, carPenalty, totalPenalty;

    orders = this.m_orders;
    totalProfit = 0l;
    totalPenalty = 0l;
    hadCar = false;

    if ((s != null) && ((schedule = s.m_schedules) != null) && ((maxCar = schedule.length) > 0)) {

      cars = this.m_cars;
      carCount = cars.length;
      if (carCount < maxCar) {
        if (console != null) {
          console.println(//
              "WARNING: Solution uses more cars than exist in the scenario, ignoring the rest."); //$NON-NLS-1$        
        }
        curCar = carCount;
      }

      orderPos = this.m_orderPos;
      if (orderPos == null) {
        this.m_orderPos = orderPos = new int[orders.length];
      }

      Arrays.fill(orderPos, Scenario.ORDER_AT_PICKUP); // all orders are at
                                                       // pickup
      // location
      orderInThisCar = -1;

      // we process one car after the other
      mainLoop: for (curCar = 0; curCar < maxCar; curCar++) {
        orderInThisCar--;

        // are any tasks scheduled for this car?
        carTasks = schedule[curCar];
        if ((carTasks == null) || ((curCarTaskCount = carTasks.length) <= 0)) {
          continue mainLoop;
        }
        hadCar = true;

        if (console != null) {
          console.print("Now processing car "); //$NON-NLS-1$
          console.print(curCar);
          console.print(" which is initially located at depot "); //$NON-NLS-1$
          console.println(Scenario.DEPOT_LOCATION);
        }

        // setup the car's capacity
        curCarRemainingCapacity = curCarCapacity = cars[curCar];
        carProfit = 0;
        curCarLoc = 0;
        carPenalty = 0l;

        carLoop: for (curCarTaskIndex = 0; curCarTaskIndex < curCarTaskCount; curCarTaskIndex++) {
          orderID = carTasks[curCarTaskIndex];
          order = orders[orderID];

          if (orderPos[orderID] == Scenario.ORDER_AT_PICKUP) {
            // the order is at its pickup location. we have to driver there and
            // pick it up
            nextLoc = order[Scenario.ORDER_PICKUP_LOCATION_INDEX];
            weight = order[Scenario.ORDER_WEIGHT_INDEX];
            if (nextLoc != curCarLoc) {
              curCost = this.travelCost(curCarLoc, nextLoc);
              carProfit -= curCost;
            } else {
              curCost = 0;
            }

            if (console != null) {
              console.print("Car ");//$NON-NLS-1$
              console.print(curCar);
              if (nextLoc != curCarLoc) {
                console.print(" travels from location ");//$NON-NLS-1$
                console.print(curCarLoc);
                console.print(" to location ");//$NON-NLS-1$
                console.print(nextLoc);
                console.print(" (which costs ");//$NON-NLS-1$
                console.print(curCost);
                console.print("RMB) and");//$NON-NLS-1$
              }

              console.print(" picks up order "); //$NON-NLS-1$
              console.print(orderID);
              console.print(" with weight "); //$NON-NLS-1$
              console.print(weight);
              console.print("kg");//$NON-NLS-1$

              if (weight > curCarRemainingCapacity) {
                console.println();
                console.print("ERROR! Remaining capacity of ");//$NON-NLS-1$
                console.print(curCarRemainingCapacity);
                console.print("kg is too small for weight ");//$NON-NLS-1$
                console.print(weight);
                console.print(". A penalty of ");//$NON-NLS-1$
                console.print(Scenario.PHYSICAL_VIOLATION_PENALTY);
                console.println(//
                    " is added to objective value.");//$NON-NLS-1$
                carPenalty += Scenario.PHYSICAL_VIOLATION_PENALTY;
              } else {
                console.print(", remaining capacity: ");//$NON-NLS-1$
                console.print(curCarRemainingCapacity - weight);
                console.println("kg.");//$NON-NLS-1$
              }
            } else {
              if (curCarRemainingCapacity < weight) {
                carPenalty += Scenario.PHYSICAL_VIOLATION_PENALTY;
              }
            }

            curCarRemainingCapacity -= weight;
            curCarLoc = nextLoc;
            orderPos[orderID] = orderInThisCar;
            continue carLoop;
          }

          if (orderPos[orderID] == orderInThisCar) {
            // the order is inside the car. we have to drive to its destination
            nextLoc = order[Scenario.ORDER_DELIVERY_LOCATION_INDEX];
            weight = order[Scenario.ORDER_WEIGHT_INDEX];
            profit = order[Scenario.ORDER_PROFIT_INDEX];

            if (nextLoc != curCarLoc) {
              curCost = this.travelCost(curCarLoc, nextLoc);
              carProfit -= curCost;
            } else {
              curCost = 0;
            }

            if (console != null) {
              console.print("Car ");//$NON-NLS-1$
              console.print(curCar);
              if (nextLoc != curCarLoc) {
                console.print(" travels from location ");//$NON-NLS-1$
                console.print(curCarLoc);
                console.print(" location to ");//$NON-NLS-1$
                console.print(nextLoc);
                console.print(" (which costs ");//$NON-NLS-1$
                console.print(curCost);
                console.print("RMB) and");//$NON-NLS-1$
              }
              console.print(" delivers order "); //$NON-NLS-1$
              console.print(orderID);
              console.print(" with weight "); //$NON-NLS-1$
              console.print(weight);
              console.print("kg, remaining capacity: ");//$NON-NLS-1$
              curCarRemainingCapacity = Math.min(curCarRemainingCapacity + weight, curCarCapacity);
              console.print(curCarRemainingCapacity);
              console.print("kg which leads to a profit of ");//$NON-NLS-1$
              console.print(profit);
              console.println("RMB.");//$NON-NLS-1$
            } else {
              curCarRemainingCapacity = Math.min(curCarRemainingCapacity + weight, curCarCapacity);
            }
            curCarLoc = nextLoc;
            carProfit += profit;
            orderPos[orderID] = Scenario.ORDER_AT_DELIVERY;
            continue carLoop;
          }

          if (orderPos[orderID] == Scenario.ORDER_AT_DELIVERY) {
            if (console != null) {
              console.print("WARNING: Order ");//$NON-NLS-1$
              console.print(orderID);
              console.println(//
                  " has already been delivered and is ignored now.");//$NON-NLS-1$
            }
            continue carLoop;
          }

          if (console != null) {
            console.print("WARNING: Order ");//$NON-NLS-1$
            console.print(orderID);
            console.println(" is in another car and therefore ignored now.");//$NON-NLS-1$
          }
        }

        if (Scenario.DEPOT_LOCATION != curCarLoc) {
          curCost = this.travelCost(curCarLoc, Scenario.DEPOT_LOCATION);
          carProfit -= curCost;
          if (console != null) {
            console.print("Car drives back home to depot ");//$NON-NLS-1$
            console.print(Scenario.DEPOT_LOCATION);
            console.print("which costs ");//$NON-NLS-1$
            console.print(curCost);
            console.print("RMB.");//$NON-NLS-1$
          }
        }

        if (console != null) {
          console.print("Car "); //$NON-NLS-1$
          console.print(curCar);
          console.print(" has finished its tour with a profit of "); //$NON-NLS-1$ 
          console.print(carProfit);
          console.print("RMB");//$NON-NLS-1$
          if (carPenalty > 0l) {
            console.print(" and ");//$NON-NLS-1$
            console.print(carPenalty);
            console.print("RMB penalty costs for physical/capacity violations");//$NON-NLS-1$
          }
          console.println();
        }

        totalProfit += carProfit;
        totalPenalty += carPenalty;
      }

      for (orderID = 0; orderID < orders.length; orderID++) {
        curOrderPos = orderPos[orderID];
        if (curOrderPos == ORDER_AT_PICKUP) {
          if (console != null) {
            console.print("Order ");//$NON-NLS-1$
            console.print(orderID);
            console.print(" has been ignored, which is OK.");//$NON-NLS-1$
          }
        } else {
          if (curOrderPos < 0) {
            if (console != null) {
              console.print("Order ");//$NON-NLS-1$
              console.print(orderID);
              console.print(" has been picked up by car ");//$NON-NLS-1$
              console.print(-curOrderPos);
              console.print(" but not delivered: Penalty of ");//$NON-NLS-1$
              console.println(UNDELIVERED_ORDER_PENALTY);
            }
            totalPenalty += UNDELIVERED_ORDER_PENALTY;
          }
        }
      }
    }

    if (!hadCar) {
      if (console != null) {
        console.println("WARNING: Solution is empty.");//$NON-NLS-1$
        totalPenalty += EMPTY_PENALTY;
      }
    }

    if (console != null) {
      console.print("The profit gained is "); //$NON-NLS-1$
      console.print(totalProfit);
      console.print("RMB and "); //$NON-NLS-1$
      if (totalPenalty > 0l) {
        console.print("a penalty of "); //$NON-NLS-1$
        console.print(totalPenalty);
        console.println("RMB occured due to errors in the solution was added."); //$NON-NLS-1$
      } else {
        console.println("the solution is fully correct.");//$NON-NLS-1$
      }
      console.print(//
          "The objective value is thus ");//$NON-NLS-1$
      totalProfit += totalPenalty;
      console.println(totalProfit);
      console.println();
    }
    return totalProfit;
  }

  /**
   * generate a problem randomly
   * 
   * @param orderNo
   *          the number of orders
   * @param locationNo
   *          the number of locations
   * @param carNo
   *          the car number
   */
  public final void generateRandom(final int orderNo, final int locationNo, final int carNo) {
    final Random r;
    final Cluster cluster;
    final int[][] locations, orders;
    final int[] cars, locPerm;
    int[] tmp;
    int i, j, k, l, maxCapa, minCapa;
    double distMin;
    boolean mix;

    this.clear();

    if (orderNo <= 0) {
      throw new IllegalArgumentException(
          "Order number must be greater or equal to one, but was set to " + orderNo); //$NON-NLS-1$
    }

    if (locationNo <= 0) {
      throw new IllegalArgumentException(
          "Location number must be greater or equal to one, but was set to " + locationNo); //$NON-NLS-1$
    }

    if (carNo <= 0) {
      throw new IllegalArgumentException(
          "Car number must be greater or equal to one, but was set to " + carNo); //$NON-NLS-1$
    }

    if (locationNo > (2 * orderNo)) {
      throw new IllegalArgumentException(
          "Location number must be smaller or equal to two times the order number (because otherwise there are useless locations), but was set to " + locationNo); //$NON-NLS-1$
    }

    this.m_locations = locations = new int[locationNo][2];
    this.m_orders = orders = new int[orderNo][4];
    this.m_cars = cars = new int[carNo];

    r = new Random();

    // create the locations: some locations are clustered, i.e., close together
    cluster = new Cluster(r);

    for (i = locationNo; (--i) >= 0;) {
      cluster.sampleCoordinates(locations[i], locations, i);
    }

    // mix the locations: make clusters less obvious
    locPerm = new int[locationNo - 1];
    for (i = locationNo; (--i) >= 0;) {
      j = r.nextInt(locationNo);

      tmp = locations[i];
      locations[i] = locations[j];
      locations[j] = tmp;
      if (i < locPerm.length) {
        locPerm[i] = (1 + i);
      }
    }

    // create vehicles
    maxCapa = 1;
    while (r.nextInt(4) > 0) {
      maxCapa += r.nextInt(100);
    }
    minCapa = Integer.MAX_VALUE;
    for (i = cars.length; (--i) >= 0;) {
      j = (100 + r.nextInt(maxCapa));
      j = (((j / 10) + 1) * 10);
      cars[i] = j;
      if (j < minCapa) {
        minCapa = j;
      }
    }

    minCapa = (1 + (minCapa >> 1));

    // now make orders

    j = 0;
    mix = true;

    for (i = orderNo; (--i) >= 0;) {
      if (mix) {
        for (j = locPerm.length; (--j) >= 0;) {
          k = r.nextInt(locPerm.length);
          l = locPerm[j];
          locPerm[j] = locPerm[k];
          locPerm[k] = l;
        }
        mix = false;
        j = 0;
      }

      tmp = orders[i];
      tmp[Scenario.ORDER_PICKUP_LOCATION_INDEX] = locPerm[j];
      j = ((j + 1) % locPerm.length);
      if (j <= 0) {
        mix = true;
        j = 0;
      }

      tmp[Scenario.ORDER_DELIVERY_LOCATION_INDEX] = locPerm[j];
      j = ((j + 1) % locPerm.length);
      if (j <= 0) {
        mix = true;
        j = 0;
      }

      tmp[Scenario.ORDER_WEIGHT_INDEX] = (1 + r.nextInt(minCapa));
    }

    // do profits
    for (i = orderNo; (--i) >= 0;) {
      tmp = orders[i];

      distMin = (this.travelCost(0, tmp[ORDER_PICKUP_LOCATION_INDEX]))
          + (this.travelCost(tmp[ORDER_PICKUP_LOCATION_INDEX], tmp[ORDER_DELIVERY_LOCATION_INDEX]))
          + (this.travelCost(tmp[ORDER_DELIVERY_LOCATION_INDEX], 0));

      for (j = orderNo; (--j) >= 0;) {
        if (i == j) {
          continue;
        }
        distMin = Math.min(distMin, 1 + (0.5d + Math.min(((this.travelCost(
            orders[j][ORDER_DELIVERY_LOCATION_INDEX], tmp[ORDER_PICKUP_LOCATION_INDEX])) + (this
            .travelCost(tmp[ORDER_PICKUP_LOCATION_INDEX], tmp[ORDER_DELIVERY_LOCATION_INDEX]))),

        ((this.travelCost(tmp[ORDER_DELIVERY_LOCATION_INDEX],
            orders[j][ORDER_PICKUP_LOCATION_INDEX])) + (this.travelCost(
            tmp[ORDER_PICKUP_LOCATION_INDEX], tmp[ORDER_DELIVERY_LOCATION_INDEX]))))));
      }

      tmp[ORDER_PROFIT_INDEX] = Math.max(1, ((int) (0.5d + (r.nextDouble() + 1.2d) * distMin)));

      // (((int) (0.5d + ((r.nextDouble() * distStdDev) + distMean)))));
    }

    i = r.nextInt(orderNo);
    tmp = orders[i];
    tmp[ORDER_PROFIT_INDEX] = (this.travelCost(0, tmp[ORDER_PICKUP_LOCATION_INDEX]))
        + (this.travelCost(tmp[ORDER_PICKUP_LOCATION_INDEX], tmp[ORDER_DELIVERY_LOCATION_INDEX]))
        + (this.travelCost(tmp[ORDER_DELIVERY_LOCATION_INDEX], 0));
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
   * Read a scenario from a file.
   * 
   * @param f
   *          the file
   * @throws IOException
   *           if io fails
   */
  public final void readFile(final File f) throws IOException {
    String s, t;
    int state, i, j, doneLocations, doneOrders, doneCars;
    int[][] locations, orders;
    int[] cars, tmpi;
    String[] tmp;

    this.clear();

    try (FileReader fr = new FileReader(f)) {
      try (BufferedReader br = new BufferedReader(fr)) {
        locations = null;
        orders = null;
        cars = null;
        state = 0;
        doneLocations = 0;
        doneOrders = 0;
        doneCars = 0;

        outer: while ((s = br.readLine()) != null) {

          while ((i = s.indexOf(Scenario.COMMENT)) >= 0) {
            s = s.substring(0, i);
          }

          s = s.trim();
          if ((j = s.length()) <= 0) {
            continue;
          }

          do {
            i = j;
            s = s.replace('\t', ' ');
            s = s.replace('\0', ' ');
            s = s.replace('\b', ' ');
            s = s.replace('\f', ' ');
            s = s.replace('\b', ' ');
            s = s.replace("  ", " "); //$NON-NLS-2$//$NON-NLS-1$
            j = s.length();
          } while (j < i);

          i = s.indexOf(':');
          if (i >= 0) {
            state = 0;
            t = s.substring(0, i).trim();

            if (t.equalsIgnoreCase(Scenario.LOCATION_NUMBER)) {
              if (locations != null) {
                throw new IOException("Location number set twice."); //$NON-NLS-1$
              }
              try {
                i = Integer.parseInt(s.substring(i + 1).trim());
              } catch (final Throwable v) {
                throw new IOException(v);
              }
              this.m_locations = locations = new int[i][];
              continue outer;
            }

            if (t.equalsIgnoreCase(Scenario.ORDER_NUMBER)) {
              if (orders != null) {
                throw new IOException("Order number set twice."); //$NON-NLS-1$
              }
              try {
                i = Integer.parseInt(s.substring(i + 1).trim());
              } catch (final Throwable v) {
                throw new IOException(v);
              }
              this.m_orders = orders = new int[i][];
              continue outer;
            }

            if (t.equalsIgnoreCase(Scenario.CAR_NUMBER)) {
              if (cars != null) {
                throw new IOException("Car number set twice."); //$NON-NLS-1$
              }
              try {
                i = Integer.parseInt(s.substring(i + 1).trim());
              } catch (final Throwable v) {
                throw new IOException(v);
              }
              this.m_cars = cars = new int[i];
              Arrays.fill(cars, -1);
              continue outer;
            }

            if (t.equalsIgnoreCase(Scenario.LOCATION_LIST)) {
              state = 1;
              continue outer;
            }

            if (t.equalsIgnoreCase(Scenario.ORDER_LIST)) {
              state = 2;
              continue outer;
            }

            if (t.equalsIgnoreCase(Scenario.CAR_LIST)) {
              state = 3;
              continue outer;
            }

            throw new IOException(//
                "Unexpected ':' encountered."); //$NON-NLS-1$
          }

          switch (state) {
            case 0: {
              continue outer;
            }

            case 1: {
              tmp = Scenario.SPLIT_PATTERN.split(s);
              if (tmp.length != 3) {
                throw new IOException("Unprocessable sequence: '" + //$NON-NLS-1$
                    s + "'.");//$NON-NLS-1$
              }
              try {
                i = Integer.parseInt(tmp[0]);
                if (locations == null) {
                  throw new IOException("Location number not specified.");//$NON-NLS-1$
                }
                tmpi = locations[i];
                if (tmpi != null) {
                  throw new IOException(//
                      "Location " + i + " defined multiple times.");//$NON-NLS-1$//$NON-NLS-2$
                }
                locations[i] = tmpi = new int[2];
                tmpi[Scenario.LOCATION_X_INDEX] = Integer.parseInt(tmp[1]);
                tmpi[Scenario.LOCATION_Y_INDEX] = Integer.parseInt(tmp[2]);
              } catch (final Throwable v) {
                throw new IOException(v);
              }
              doneLocations++;
              continue outer;
            }

            case 2: {
              tmp = Scenario.SPLIT_PATTERN.split(s);
              if (tmp.length != 5) {
                throw new IOException("Unprocessable sequence: '" + //$NON-NLS-1$
                    s + "'.");//$NON-NLS-1$
              }
              try {
                i = Integer.parseInt(tmp[0]);
                if (orders == null) {
                  throw new IOException("Order number not specified.");//$NON-NLS-1$
                }
                tmpi = orders[i];
                if (tmpi != null) {
                  throw new IOException(//
                      "Order " + i + " defined multiple times.");//$NON-NLS-1$//$NON-NLS-2$
                }
                orders[i] = tmpi = new int[4];

                tmpi[Scenario.ORDER_PICKUP_LOCATION_INDEX] = Integer.parseInt(tmp[1]);
                tmpi[Scenario.ORDER_DELIVERY_LOCATION_INDEX] = Integer.parseInt(tmp[2]);
                tmpi[Scenario.ORDER_WEIGHT_INDEX] = Integer.parseInt(tmp[3]);
                tmpi[Scenario.ORDER_PROFIT_INDEX] = Integer.parseInt(tmp[4]);
              } catch (final Throwable v) {
                throw new IOException(v);
              }
              doneOrders++;
              continue outer;
            }

            case 3: {
              tmp = Scenario.SPLIT_PATTERN.split(s);
              if (tmp.length != 2) {
                throw new IOException("Unprocessable sequence: '" + //$NON-NLS-1$
                    s + "'.");//$NON-NLS-1$
              }
              try {
                i = Integer.parseInt(tmp[0]);

                if (cars == null) {
                  throw new IOException("Car number not specified.");//$NON-NLS-1$
                }
                if (cars[i] != (-1)) {
                  throw new IOException(//
                      "Car " + i + " defined multiple times.");//$NON-NLS-1$//$NON-NLS-2$
                }

                cars[i] = Integer.parseInt(tmp[1]);
              } catch (final Throwable v) {
                throw new IOException(v);
              }
              doneCars++;
              continue outer;
            }

            default: {
              throw new IOException("This should never happen.");//$NON-NLS-1$
            }
          }

        }

        if (locations == null) {
          throw new IOException("No locations specified.");//$NON-NLS-1$
        }
        if (locations.length > doneLocations) {
          throw new IOException("Not enough locations specified.");//$NON-NLS-1$
        }

        if (orders == null) {
          throw new IOException("No orders specified.");//$NON-NLS-1$
        }
        if (orders.length > doneOrders) {
          throw new IOException("Not enough orders specified.");//$NON-NLS-1$
        }

        if (cars == null) {
          throw new IOException("No cars specified.");//$NON-NLS-1$
        }
        if (cars.length > doneCars) {
          throw new IOException("Not enough cars specified.");//$NON-NLS-1$
        }
      }

    }
  }

  /**
   * Get the x-coordinate of a location.
   * 
   * @param index
   *          the location index
   * @return the x-coordinate of the location
   */
  public final int locationX(final int index) {
    return this.m_locations[index][Scenario.LOCATION_X_INDEX];
  }

  /**
   * Get the y-coordinate of a location.
   * 
   * @param index
   *          the location index
   * @return the y-coordinate of the location
   */
  public final int locationY(final int index) {
    return this.m_locations[index][Scenario.LOCATION_Y_INDEX];
  }

  /**
   * write to a console
   * 
   * @param console
   *          the stream to print to
   */
  public final void print(final PrintStream console) {
    int i, j;

    console.println();
    console.print(Scenario.COMMENT);
    console.println(//
        " A scenario is composed of a set of locations, set of orders, and a set of cars"); //$NON-NLS-1$
    console.print(Scenario.COMMENT);
    console.println(//
        " Each order may provide a profit, while travelling to a location causes a cost."); //$NON-NLS-1$
    console.print(Scenario.COMMENT);
    console.println(//
        " A solution is a plan how to deliver all or at least some of the orders while never violating the car's capacity constraints."); //$NON-NLS-1$

    console.println();
    console.println();
    console.print(Scenario.COMMENT);
    console.println(//
        " -------------- LOCATIONS --------------"); //$NON-NLS-1$    
    console.print(Scenario.COMMENT);
    console.println(//
        " Each location has an ID, an x- and a y-coordinate."); //$NON-NLS-1$
    console.print(Scenario.COMMENT);
    console.println(//
        " The location with ID 0 is the depot where all cars start and have to return to."); //$NON-NLS-1$
    console.print(Scenario.COMMENT);
    console.println(//
        " The cost to travel from one locations to another is the Euclidean distance of their coordinates in the 2D plane, rounded up to the next full integer."); //$NON-NLS-1$

    console.println();
    console.print(Scenario.LOCATION_NUMBER);
    console.print(": "); //$NON-NLS-1$
    j = this.locationCount();
    console.println(j);
    console.print(Scenario.LOCATION_LIST);
    console.print(": ");//$NON-NLS-1$

    console.print(Scenario.COMMENT);
    console.println(" ID X-COORDINATE Y-COORDINATE");//$NON-NLS-1$

    for (i = 0; i < j; i++) {
      console.print(i);
      console.print(' ');
      console.print(this.locationX(i));
      console.print(' ');
      console.print(this.locationY(i));

      if (i <= 0) {
        console.print(' ');
        console.print(Scenario.COMMENT);
        console.print(" <-- depot");//$NON-NLS-1$
      }

      console.println();
    }

    console.println();
    console.println();
    console.print(Scenario.COMMENT);
    console.println(//
        " -------------- ORDERS --------------"); //$NON-NLS-1$    
    console.print(Scenario.COMMENT);
    console.println(//
        " Each order has an ID, a pick-up location, a destination location, a weight, and a profit."); //$NON-NLS-1$
    console.print(Scenario.COMMENT);
    console.println(//
        " A car needs to drive to the order's pick-up location (which incurs a cost), pick the order up, drive to its destination location (which incurs a cost), and unload it there (which profits the profit)."); //$NON-NLS-1$
    console.print(Scenario.COMMENT);
    console.println(//
        " However, the total weight a car can transport at once is limited (see later)."); //$NON-NLS-1$

    console.println();
    console.print(Scenario.ORDER_NUMBER);
    console.print(": "); //$NON-NLS-1$
    j = this.orderCount();
    console.println(j);
    console.print(Scenario.ORDER_LIST);
    console.print(": ");//$NON-NLS-1$

    console.print(Scenario.COMMENT);
    console.println(" ID PICK-UP-LOCATION-ID DELIVERY-LOCATION-ID WEIGHT PROFIT");//$NON-NLS-1$

    for (i = 0; i < j; i++) {
      console.print(i);
      console.print(' ');
      console.print(this.orderPickupLocation(i));
      console.print(' ');
      console.print(this.orderDeliveryLocation(i));
      console.print(' ');
      console.print(this.orderWeight(i));
      console.print(' ');
      console.println(this.orderProfit(i));
    }

    console.println();
    console.println();
    console.print(Scenario.COMMENT);
    console.println(//
        " -------------- CARS --------------"); //$NON-NLS-1$    
    console.print(Scenario.COMMENT);
    console.println(//
        " Each car has an ID and a capacity."); //$NON-NLS-1$
    console.print(Scenario.COMMENT);
    console.println(//
        " All cars are initially located at the depot (the location with ID 0) and need to return to there at the end."); //$NON-NLS-1$
    console.print(Scenario.COMMENT);
    console.println(//
        " No car can load more weight as its capacity limit allows."); //$NON-NLS-1$
    console.print(Scenario.COMMENT);
    console.println(//
        " Cars transport orders from their pick-up locations to their delivery locations."); //$NON-NLS-1$

    console.println();
    console.print(Scenario.CAR_NUMBER);
    console.print(": "); //$NON-NLS-1$
    j = this.carCount();
    console.println(j);
    console.print(Scenario.CAR_LIST);
    console.print(": ");//$NON-NLS-1$

    console.print(Scenario.COMMENT);
    console.println(" ID CAPACITY-LIMIT");//$NON-NLS-1$

    for (i = 0; i < j; i++) {
      console.print(i);
      console.print(' ');
      console.println(this.carCapacity(i));
    }
  }

  /**
   * The main method
   * 
   * @param args
   *          the argument list
   * @throws Throwable
   *           if something goes wrong
   */
  public static final void main(final String[] args) throws Throwable {
    File target;
    final Random r;
    Scenario p;
    int nOrders, nLocations, nCars;
    String fo;

    System.out.println("TestSet Generator"); //$NON-NLS-1$
    System.out.print("Usage: java "); //$NON-NLS-1$
    System.out.print(Scenario.class.getCanonicalName());
    System.out.println(" {path to output file}");//$NON-NLS-1$

    p = new Scenario();

    fo = null;
    if (args != null) {
      if (args.length > 0) {
        fo = args[0];
        if (fo != null) {
          fo = fo.trim();
          if (fo.length() <= 0) {
            fo = null;
          }
        }
      }
    }

    System.out.println("Generating random scenarios."); //$NON-NLS-1$
    r = new Random();

    for (int scen = 1; scen <= 5; scen++) {

      nOrders = Math.max(1, ((int) (0.5d + ((scen * 10) + r.nextGaussian()))));
      nCars = ((3 * scen) >> 1);
      nLocations = (nOrders + 1 + (int) (Math.max(r.nextDouble(),
          Math.max(r.nextDouble(), r.nextDouble())) * nOrders));

      p.generateRandom(nOrders, nLocations, nCars);

      System.out.println();
      p.print(System.out);
      System.out.println();

      if (fo != null) {
        target = new File(new File(fo).getCanonicalFile(), "example_" + scen + ".txt");//$NON-NLS-1$//$NON-NLS-2$
        System.out.println("Target file: " + target); //$NON-NLS-1$
        p.writeFile(target);
      }
    }
  }

  /** the dummy variable */
  static final int[][] DUMMY_I2D = new int[0][0];
  /** the dummy variable */
  static final int[] DUMMY_I1D = new int[0];

  /** the location x index */
  static final int LOCATION_X_INDEX = 0;
  /** the location x index */
  static final int LOCATION_Y_INDEX = (Scenario.LOCATION_X_INDEX + 1);

  /** the order pickup location index */
  static final Pattern SPLIT_PATTERN = Pattern.compile("\\s+");//$NON-NLS-1$

  /** the order pickup location index */
  private static final int ORDER_PICKUP_LOCATION_INDEX = 0;

  /** the order delivery location index */
  private static final int ORDER_DELIVERY_LOCATION_INDEX = (Scenario.ORDER_PICKUP_LOCATION_INDEX + 1);

  /** the order weight index */
  private static final int ORDER_WEIGHT_INDEX = (Scenario.ORDER_DELIVERY_LOCATION_INDEX + 1);

  /** the order profit index */
  private static final int ORDER_PROFIT_INDEX = (Scenario.ORDER_WEIGHT_INDEX + 1);

  /** the order is at pickup location */
  private static final int ORDER_AT_PICKUP = (Integer.MIN_VALUE);
  /** the order is at delivery location */
  private static final int ORDER_AT_DELIVERY = (Scenario.ORDER_AT_PICKUP + 1);

  /** a cluster of cities or locations */
  static final class Cluster {

    /** the default x-coordinate scale */
    private static final double SCALE_X = 10000.0d;

    /** the default y-coordinate scale */
    private static final double SCALE_Y = 10000.0d;

    /** the default x-coordinate stddev */
    private static final double STDDEV_X = Math.sqrt(Cluster.SCALE_X);

    /** the default y-coordinate stddev */
    private static final double STDDEV_Y = Math.sqrt(Cluster.SCALE_Y);

    /** the x-coordinate of the center */
    private double m_centerX;

    /** the y-coordinate of the center */
    private double m_centerY;

    /** the x stddev */
    private double m_stddevX;

    /** the y stddev */
    private double m_stddevY;

    /** the randomizer */
    private final Random m_rand;

    /**
     * create a cluster
     * 
     * @param r
     *          the randomizer
     */
    Cluster(final Random r) {
      super();
      this.m_rand = r;
      this.init();
    }

    /** initialize */
    final void init() {
      final Random r;

      r = this.m_rand;

      this.m_centerX = (r.nextGaussian() * Cluster.SCALE_X);
      this.m_centerY = (r.nextGaussian() * Cluster.SCALE_Y);

      do {
        this.m_stddevX = (r.nextDouble() * Cluster.STDDEV_X);
      } while (this.m_stddevX < 1d);

      do {
        this.m_stddevY = (r.nextDouble() * Cluster.STDDEV_Y);
      } while (this.m_stddevY < 1d);
    }

    /**
     * Sample a set of coordinates
     * 
     * @param dest
     *          the dest coordinate pair
     * @param existing
     *          the list of existing coordinates
     * @param count
     *          the number of existing coordinates
     */
    final void sampleCoordinates(final int[] dest, final int[][] existing, final int count) {
      int x, y;
      final Random r;
      int i;
      boolean exists;

      r = this.m_rand;

      outer: for (;;) {
        x = ((int) (Math.rint(this.m_centerX + (r.nextGaussian() * this.m_stddevX))));
        y = ((int) (Math.rint(this.m_centerY + (r.nextGaussian() * this.m_stddevY))));

        exists = false;
        inner: for (i = existing.length; (--i) > count;) {
          if ((existing[i][Scenario.LOCATION_X_INDEX] == x)
              || (existing[i][Scenario.LOCATION_Y_INDEX] == y)) {
            exists = true;
            break inner;
          }
        }

        if (r.nextInt(10) <= 0) {
          this.init();
        }
        if (!exists) {
          break outer;
        }
      }

      dest[Scenario.LOCATION_X_INDEX] = x;
      dest[Scenario.LOCATION_Y_INDEX] = y;
    }

  }

}
