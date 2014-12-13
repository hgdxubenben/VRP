package poad.examples.robocode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import poad.IObjectiveFunction;

/** some utilities for accessing robocode */
public class RobocodeObjective implements IObjectiveFunction<String> {
  /** the path to robocode */
  private final File m_robocode_dir;

  /** the path to robocode */
  private final File m_opti_bot_config;

  /** the path to robocode */
  private final File m_opti_battle_res;

  /** the database which must be deleted */
  private final File m_db_delete;

  /** the path to robocode */
  private final ProcessBuilder m_opti_battle;

  /** the robot name */
  private final String m_robotName;

  /** the temporary output file */
  private final File m_temp;

  /** the constructor */
  public RobocodeObjective() {
    super();
    this.m_robocode_dir = this.getRobocodeDir();
    this.m_opti_bot_config = this.getOptiBotConfig();
    this.m_opti_battle_res = this.getBattleOutput();
    this.m_db_delete = this.getDBDelete();
    this.m_temp = this.getTempOutput();
    this.m_opti_battle = this.makeProcessBuilder();
    this.m_robotName = "optibot.OptiBot*".toLowerCase(); //$NON-NLS-1$
  }

  /**
   * obtain the robocode directory
   * 
   * @return the robocode directory
   */
  private final File getRobocodeDir() {
    URL url;
    File file;
    try {
      url = RobocodeObjective.class.getResource("/resources/robocode/robocode.bat");
      try {
        file = new File(url.toURI());
      } catch (final URISyntaxException e) {
        file = new File(url.getPath());
      }
      file = file.getParentFile();
      try {
        return file.getCanonicalFile();
      } catch (final Throwable t) {
        return file.getAbsoluteFile();
      }
    } catch (final Throwable tt) {
      file = new File("./resources/robocode");
      try {
        return file.getCanonicalFile();
      } catch (final Throwable t) {
        return file.getAbsoluteFile();
      }
    }
  }

  /**
   * get the optibot configuration file
   * 
   * @return the path to the optibot configuration
   */
  private final File getOptiBotConfig() {
    File file;
    file = new File(new File(//
        new File(this.m_robocode_dir, "robots"), "optibot"), "OptiBot.cfg");
    try {
      return file.getCanonicalFile();
    } catch (final Throwable t) {
      return file.getAbsoluteFile();
    }
  }

  /**
   * get the optibot configuration file
   * 
   * @return the path to the optibot configuration
   */
  private final File getTempOutput() {
    File file;
    file = new File(this.m_robocode_dir, "lastOutput.txt"); //$NON-NLS-1$
    try {
      return file.getCanonicalFile();
    } catch (final Throwable t) {
      return file.getAbsoluteFile();
    }
  }

  /**
   * obtain the file which must be deleted
   * 
   * @return the file which must be deleted
   */
  private final File getDBDelete() {
    File file;
    file = new File(new File(this.m_robocode_dir,//
        "robots"), "robot.database"); //$NON-NLS-1$ //$NON-NLS-2$
    try {
      return file.getCanonicalFile();
    } catch (final Throwable t) {
      return file.getAbsoluteFile();
    }
  }

  /**
   * get the battle output file
   * 
   * @return the path to the battle output file
   */
  private final File getBattleOutput() {
    File file;
    file = new File(this.m_robocode_dir, "optiBattleResults.txt"); //$NON-NLS-1$
    try {
      return file.getCanonicalFile();
    } catch (final Throwable t) {
      return file.getAbsoluteFile();
    }
  }

  /**
   * get the battle file
   * 
   * @return the path to the output file
   */
  private final File getBattleFile() {
    File file;
    file = new File(new File(this.m_robocode_dir, "battles"), //$NON-NLS-1$
        "optiBattle.battle"); //$NON-NLS-1$
    try {
      return file.getCanonicalFile();
    } catch (final Throwable t) {
      return file.getAbsoluteFile();
    }
  }

  /**
   * get the libs directory
   * 
   * @return the path to the libs directory
   */
  private final File getRobocodeLibsDir() {
    File file;
    file = new File(this.m_robocode_dir, "libs"); //$NON-NLS-1$
    try {
      return file.getCanonicalFile();
    } catch (final Throwable t) {
      return file.getAbsoluteFile();
    }
  }

  /**
   * make the process builder to be used for robocode
   * 
   * @return the process builder
   */
  private final ProcessBuilder makeProcessBuilder() {
    ProcessBuilder psb;
    ArrayList<String> cmd;
    StringBuilder sb;
    File[] fs;
    File v;
    cmd = new ArrayList<>();
    cmd.add("java"); //$NON-NLS-1$
    cmd.add("-Xmx1024M");//$NON-NLS-1$
    cmd.add("-DRANDOMSEED=18071987");//$NON-NLS-1$
    cmd.add("-cp");//$NON-NLS-1$
    sb = new StringBuilder();
    fs = this.getRobocodeLibsDir().listFiles();
    if (fs != null) {
      for (final File f : fs) {
        if (f.exists() && (f.isFile())) {
          if (sb.length() > 0) {
            sb.append(';');
          }
          try {
            sb.append(f.getCanonicalPath());
          } catch (final Throwable t) {
            sb.append(f.getAbsolutePath());
          }
        }
      }
    }
    cmd.add(sb.toString());
    cmd.add("robocode.Robocode"); //$NON-NLS-1$
    cmd.add("-battle");//$NON-NLS-1$
    v = this.getBattleFile();
    try {
      cmd.add(v.getCanonicalPath());
    } catch (final Throwable tt) {
      cmd.add(v.getAbsolutePath());
    }
    cmd.add("-results");//$NON-NLS-1$
    try {
      cmd.add(this.m_opti_battle_res.getCanonicalPath());
    } catch (final Throwable tt) {
      cmd.add(this.m_opti_battle_res.getAbsolutePath());
    }
    cmd.add("-nodisplay");//$NON-NLS-1$
    cmd.add("-nosound");//$NON-NLS-1$
    psb = new ProcessBuilder();
    psb.command(cmd);
    psb.directory(this.m_robocode_dir);
    psb.inheritIO();
    psb.redirectError(this.m_temp);
    psb.redirectOutput(this.m_temp);
    return psb;
  }

  /**
   * make the config file
   * 
   * @param x
   *          the phenotype
   */
  private final void makeConfigFile(final String x) {
    try {
      try (FileWriter fw = new FileWriter(this.m_opti_bot_config)) {
        fw.write(x);
      }
    } catch (final Throwable tt) {
      tt.printStackTrace();
    }
  }

  /** clean up */
  private final void cleanup() {
    try {
      this.m_db_delete.delete();
    } catch (final Throwable tt) {
      //
    }
    try {
      this.m_opti_battle_res.delete();
    } catch (final Throwable tt) {
      //
    }
    try {
      this.m_opti_bot_config.delete();
    } catch (final Throwable tt) {
      //
    }
    try {
      this.m_temp.delete();
    } catch (final Throwable tt) {
      //
    }
  }

  /** run the battle */
  private final void runBattle() {
    final Process p;
    try {
      p = this.m_opti_battle.start();
      if (p != null) {
        try {
          p.waitFor();
        } finally {
          p.destroy();
        }
      }
    } catch (final Throwable tt) {
      tt.printStackTrace();
    }
  }

  /**
   * get the result
   * 
   * @return the result
   */
  private final double getResult() {
    String s;
    int i, j, l;
    try {
      try (FileReader fr = new FileReader(this.m_opti_battle_res)) {
        try (BufferedReader br = new BufferedReader(fr)) {
          while ((s = br.readLine()) != null) {
            s = s.trim();
            if ((l = s.length()) <= 0) {
              continue;
            }
            s = s.toLowerCase();
            i = s.indexOf(this.m_robotName);
            if (i < 0) {
              continue;
            }
            i += this.m_robotName.length();
            while ((i < l) && (s.charAt(i) <= ' ')) {
              i++;
            }
            j = i;
            while ((j < l) && (s.charAt(j) > ' ')) {
              j++;
            }
            if (i < j) {
              try {
                return Double.parseDouble(s.substring(i, j));
              } catch (final Throwable tt) {
                return Double.POSITIVE_INFINITY;
              }
            }
          }
        }
      }
    } catch (final Throwable tt) {
      tt.printStackTrace();
    }
    return Double.POSITIVE_INFINITY;
  }

  /** {@inheritDoc} */
  @Override
  public final double compute(final String x) {
    double res, realRes;
    this.cleanup();
    this.makeConfigFile(x);
    this.runBattle();
    res = this.getResult();
    realRes = -Math.max(1d, Math.round(res / 100d));
    System.out.println("Score " + res + " (" + //$NON-NLS-1$+//$NON-NLS-2$
        realRes + ") for robot '" + x + '\''); //$NON-NLS-1$
    return realRes;
  }
}
