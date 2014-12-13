package optibot;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;

import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;
import robocode.util.Utils;

/** a robot loading its behavior from an input file */
public class OptiBot extends AdvancedRobot {

  /** the behavior variable */
  private static final int INIT_GUN_TURN_AMOUNT = 0;
  /** the behavior variable */
  private static final int COUNT_ADDER = 1;
  /** the behavior variable */
  private static final int COUNT_LIMIT = 2;
  /** the behavior variable */
  private static final int GUN_TURN_AMOUNT_OVER_LIMIT = 3;
  /** the behavior variable */
  private static final int COUNT_LIMIT_2 = 4;
  /** the behavior variable */
  private static final int COUNT_LIMIT_RESET_TRACK = 5;
  /** the behavior variable */
  private static final int RESET_COUNT = 6;
  /** the behavior variable */
  private static final int MAX_DIST = 7;
  /** the behavior variable */
  private static final int DIST_MOVE = 8;
  /** the behavior variable */
  private static final int FIRE = 9;
  /** the behavior variable */
  private static final int MIN_DIST = 10;
  /** the behavior variable */
  private static final int DIST_BACK = 11;
  /** the behavior variable */
  private static final int DIST_FORWARD = 12;
  /** the behavior variable */
  private static final int FIRE_2 = 13;
  /** the behavior variable */
  private static final int DIST_BACK_2 = 14;
  /** the behavior variable */
  private static final int RANDOM_01 = 15;
  /** the behavior variable */
  private static final int RANDOM_02 = 16;
  /** the behavior variable */
  private static final int ESCAPE_ANGLE = 17;
  /** the behavior variable */
  private static final int ESCAPE_DIST = 18;
  /** the behavior variable */
  private static final int RANDOM_FIRE = 19;
  /** the behavior variable */
  private static final int RANDOM_FIRE_POWER = 20;

  /** the maximum parameter */
  private static final int MAX_PARAM = RANDOM_FIRE_POWER;

  /** the robot's behavior */
  public static final double[] BEHAVIOR = new double[MAX_PARAM + 1];

  /** internal data 1 */
  private double m_count;
  /** internal data 2 */
  private double m_gunTurnAmt;
  /** internal data 3 */
  private String m_trackName;

  /** a random decision utility */
  private final Random m_rand;

  /** the constructor */
  public OptiBot() {
    super();
    this.m_count = 0;
    this.m_gunTurnAmt = 0d;
    this.m_trackName = null;
    this.m_rand = Utils.getRandom();
  }

  /** load the configuration */
  public void loadFile() {
    String s;
    StringBuilder sb;
    int i, a, b;

    Arrays.fill(BEHAVIOR, 0d);
    sb = new StringBuilder();
    i = 0;
    try {
      try (InputStream is = OptiBot.class.getResourceAsStream("OptiBot.cfg")) { //$NON-NLS-1$
        try (InputStreamReader isr = new InputStreamReader(is)) {
          try (BufferedReader br = new BufferedReader(isr)) {
            while (((s = br.readLine()) != null) && (i < BEHAVIOR.length)) {
              s = s.trim();
              if (s.length() > 0) {
                if (sb.length() > 0) {
                  sb.append(' ');
                }
                sb.append(s);
              }
            }
          }
        }
      }
    } catch (Throwable ttt) {
      // ignore!
    }

    try {
      s = sb.toString();
      b = 0;
      for (;;) {
        a = b;
        b = s.indexOf(' ', a);
        if (b < 0) {
          break;
        }
        try {
          BEHAVIOR[i] = Double.parseDouble(s.substring(a, b));
          i++;
        } catch (Throwable vvv) {
          //
        }
        b++;
      }
    } catch (Throwable tt) {
      //
    }
  }

  /**
   * run: Tracker's main run function
   */
  @Override
  public void run() {
    // load data
    this.loadFile();

    // Set colors
    this.setBodyColor(Color.red);
    this.setScanColor(Color.red);
    this.setBulletColor(Color.red);

    // Prepare gun
    this.m_trackName = null; // Initialize to not tracking anyone
    this.setAdjustGunForRobotTurn(true); // Keep the gun still when we turn
    this.m_gunTurnAmt = map(BEHAVIOR[INIT_GUN_TURN_AMOUNT], 10d);

    // Loop forever
    while (true) {
      if (this.m_trackName != null) {
        if (this.m_rand.nextDouble() < BEHAVIOR[RANDOM_FIRE]) {
          this.setFire(map(BEHAVIOR[RANDOM_FIRE_POWER], 3d));
        }
      }

      // turn the Gun (looks for enemy)
      this.setTurnGunRight(this.m_gunTurnAmt);
      // Keep track of how long we've been looking
      this.m_count += map(BEHAVIOR[COUNT_ADDER], 1d);
      // If we've haven't seen our target for 2 turns, look left
      if (this.m_count > map(BEHAVIOR[COUNT_LIMIT], 2d)) {
        this.m_gunTurnAmt = (map(BEHAVIOR[GUN_TURN_AMOUNT_OVER_LIMIT], -10d));
      }
      // If we still haven't seen our target for 5 turns, look right
      if (this.m_count > (map(BEHAVIOR[COUNT_LIMIT_2], 5d))) {
        this.m_gunTurnAmt = map(BEHAVIOR[INIT_GUN_TURN_AMOUNT], 10d);
      }
      // If we *still* haven't seen our target after 10 turns, find another
      // target
      if (this.m_count > map(BEHAVIOR[COUNT_LIMIT_RESET_TRACK], 10d)) {
        this.m_trackName = null;
      }

      if (BEHAVIOR[RANDOM_01] > this.m_rand.nextDouble()) {
        if (this.getOthers() > 1) {
          this.m_trackName = null;
        }
      }

      this.execute();
    }
  }

  /**
   * map a value to an original value
   * 
   * @param val
   *          the evolved value
   * @param orig
   *          the original value
   * @return the mapped value
   */
  private static final double map(final double val, final double orig) {
    return ((1d + val) * orig);
  }

  /**
   * onScannedRobot: Here's the good stuff
   */
  @Override
  public void onHitByBullet(final HitByBulletEvent e) {
    if (BEHAVIOR[RANDOM_02] > this.m_rand.nextDouble()) {
      double deg = map(this.m_rand.nextGaussian() * BEHAVIOR[ESCAPE_ANGLE],
          e.getBearing() + 90);
      this.setTurnRight(deg);
      this.setAhead(map(BEHAVIOR[ESCAPE_DIST], 50d));
      this.execute();
    }
  }

  /**
   * onScannedRobot: Here's the good stuff
   */
  @Override
  public void onScannedRobot(final ScannedRobotEvent e) {

    // If we have a target, and this isn't it, return immediately
    // so we can get more ScannedRobotEvents.
    if ((this.m_trackName != null)
        && (!(e.getName().equals(this.m_trackName)))) {
      return;
    }

    // If we don't have a target, well, now we do!
    if (this.m_trackName == null) {
      this.m_trackName = e.getName();
    }

    // This is our target. Reset count (see the run method)
    this.m_count = BEHAVIOR[RESET_COUNT];

    // If our target is too far away, turn and move toward it.
    if (e.getDistance() > map(BEHAVIOR[MAX_DIST], 150d)) {
      this.m_gunTurnAmt = Utils.normalRelativeAngleDegrees(e.getBearing()
          + (this.getHeading() - this.getRadarHeading()));

      this.setTurnGunRight(this.m_gunTurnAmt); // Try changing these to
      // setTurnGunRight,
      this.setTurnRight(e.getBearing()); // and see how much Tracker
                                         // improves...
      // (you'll have to make Tracker an AdvancedRobot)
      this.setAhead(e.getDistance() - map(BEHAVIOR[DIST_MOVE], 140d));
      this.execute();
      return;
    }

    // Our target is close.
    this.m_gunTurnAmt = Utils.normalRelativeAngleDegrees(e.getBearing()
        + (this.getHeading() - this.getRadarHeading()));
    this.setTurnGunRight(this.m_gunTurnAmt);
    this.setFire(map(BEHAVIOR[FIRE], 3));

    // Our target is too close! Back up.
    if (e.getDistance() < map(BEHAVIOR[MIN_DIST], 100d)) {
      if ((e.getBearing() > -90) && (e.getBearing() <= 90)) {
        this.setBack(map(BEHAVIOR[DIST_BACK], 40d));
      } else {
        this.setAhead(map(BEHAVIOR[DIST_FORWARD], 40d));
      }
    }
    this.execute();
    this.scan();
  }

  /**
   * onHitRobot: Set him as our new target
   */
  @Override
  public void onHitRobot(final HitRobotEvent e) {
    // Only print if he's not already our target.
    if ((this.m_trackName != null) && !this.m_trackName.equals(e.getName())) {
      this.out.println("Tracking " + e.getName() + //$NON-NLS-1$
          " due to collision"); //$NON-NLS-1$
    }
    // Set the target
    this.m_trackName = e.getName();
    // Back up a bit.
    // Note: We won't get scan events while we're doing this!
    // An AdvancedRobot might use setBack(); execute();
    this.m_gunTurnAmt = Utils.normalRelativeAngleDegrees(e.getBearing()
        + (this.getHeading() - this.getRadarHeading()));
    this.setTurnGunRight(this.m_gunTurnAmt);
    this.setFire(map(BEHAVIOR[FIRE_2], 3d));
    this.setBack(map(BEHAVIOR[DIST_BACK_2], 50));
    this.execute();
  }

  /**
   * onWin: Do a victory dance
   */
  @Override
  public void onWin(final WinEvent e) {
    for (int i = 0; i < 50; i++) {
      this.turnRight(30);
      this.turnLeft(30);
    }
  }
}
