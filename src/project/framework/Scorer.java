package project.framework;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

import project.framework.stat.BivariateTestResult;
import project.framework.stat.MannWhitneyUTest;

/**
 * The scorer class takes a set of directories which contain the experimental results obtained with
 * the evaluator. It then computes a score for each approach based on the specifications of the
 * project. It will leave a file called "scores.txt" in the root folder where the scores can be
 * read. Your score must be better than those of
 * {@link project.examples.StupidSingleRandomSample},
 * {@link project.examples.RandomSamplingFromLecture}, and
 * {@link project.examples.HillClimbingFromLecture} to pass the project and get the full
 * project score, respectively.
 * 
 * @author Thomas Weise
 */
public class Scorer {

  /** the number of samples */
  public static final int SAMPLE_COUNT = 10;

  /** the significance level */
  public static final double SIGNIFICANCE_LEVEL = 0.05d;

  /**
   * The main method
   * 
   * @param args
   *          the argument list
   * @throws Throwable
   *           if something goes wrong
   */
  public static final void main(final String[] args) throws Throwable {
    final File input, output;
    final Data data;
    final int bench, algo;

    System.out.println("Algorithm Comparer / Scorer");//$NON-NLS-1$
    System.out.print("Usage: java "); //$NON-NLS-1$
    System.out.print(Scorer.class.getCanonicalName());
    System.out.println(" input_dir"); //$NON-NLS-1$

    System.out.println();
    System.out.println(//
        " input_dir = directory with one sub-directory per algorithm with,"); //$NON-NLS-1$
    System.out.println(//
        "             in turn, one sub-directry per benchmark, holding the"); //$NON-NLS-1$
    System.out.println("             results stored by the evaluator"); //$NON-NLS-1$

    System.out.println();
    if ((args == null) || (args.length < 0)) {
      System.out.println("Command line arguments incomplete, exiting.");//$NON-NLS-1$
      return;
    }

    input = new File(args[0]).getCanonicalFile();
    System.out.println("Input Dir: " + input); //$NON-NLS-1$

    output = new File(input, "scores.txt").getCanonicalFile();//$NON-NLS-1$
    System.out.println("Output File: " + output); //$NON-NLS-1$
    output.delete();

    System.out.println("Loading data...");//$NON-NLS-1$
    data = new Data();
    data.load(input);

    System.out.print("Finished loading data from ");//$NON-NLS-1$
    bench = data.benchmarkSize();
    System.out.print(bench);
    System.out.print(" benchmarks and ");//$NON-NLS-1$
    algo = data.algoSize();
    System.out.print(algo);
    System.out.println(" algorithms.");//$NON-NLS-1$

    System.out.print("Scoring...");//$NON-NLS-1$
    data.printScore(output);
    System.out.println(" ...done.");//$NON-NLS-1$
  }

  /** the internal data class */
  private static final class Data {

    /** the benchmarks */
    private final ArrayList<String> m_benchmarks;

    /** the algos */
    private final ArrayList<Algorithm> m_algos;

    /** the data */
    private final ArrayList<ArrayList<double[]>> m_data;

    /** create */
    Data() {
      super();
      this.m_benchmarks = new ArrayList<>();
      this.m_algos = new ArrayList<>();
      this.m_data = new ArrayList<>();
    }

    /**
     * get the number of benchmarks
     * 
     * @return the number of benchmarks
     */
    final int benchmarkSize() {
      return this.m_benchmarks.size();
    }

    /**
     * get the index of a benchmark, add if not yet listed
     * 
     * @param s
     *          the benchmark
     * @return the index
     */
    final int benchmarkIndex(final String s) {
      String t;
      int i;
      final int q;
      final ArrayList<String> l;
      final ArrayList<double[]> ll;

      if (s == null) {
        return -1;
      }
      t = s.trim();
      if (t.length() <= 0) {
        return -1;
      }

      t = t.toLowerCase();
      l = this.m_benchmarks;
      q = l.size();
      for (i = q; (--i) >= 0;) {
        if (t.equalsIgnoreCase(l.get(i))) {
          return i;
        }
      }

      l.add(t);

      i = this.m_algos.size();
      ll = new ArrayList<>(i);
      for (; (--i) >= 0;) {
        ll.add(Scorer.EMPTY);
      }

      this.m_data.add(ll);
      return q;
    }

    /**
     * get the number of algos
     * 
     * @return the number of algos
     */
    final int algoSize() {
      return this.m_algos.size();
    }

    /**
     * get the index of a algo, add if not yet listed
     * 
     * @param s
     *          the algo
     * @return the index
     */
    final int algoIndex(final String s) {
      String t;
      int i;
      final int q;
      final ArrayList<Algorithm> l;
      final ArrayList<ArrayList<double[]>> ll;

      if (s == null) {
        return -1;
      }
      t = s.trim();
      if (t.length() <= 0) {
        return -1;
      }

      t = t.toLowerCase();
      l = this.m_algos;
      q = l.size();
      for (i = q; (--i) >= 0;) {
        if (t.equalsIgnoreCase(l.get(i).m_name)) {
          return i;
        }
      }

      l.add(new Algorithm(t));

      i = this.m_benchmarks.size();
      ll = this.m_data;
      for (; (--i) >= 0;) {
        ll.get(i).add(Scorer.EMPTY);
      }

      return q;
    }

    /**
     * set the data for a given benchmark and algorithm
     * 
     * @param benchmark
     *          the benchmark
     * @param algo
     *          the algorithm
     * @param data
     *          the data
     */
    final void setData(final int benchmark, final int algo, final double[] data) {
      this.m_data.get(benchmark).set(algo, data);
    }

    /**
     * set the data for a given benchmark and algorithm
     * 
     * @param benchmark
     *          the benchmark
     * @param algo
     *          the algorithm
     * @return the data
     */
    final double[] getData(final int benchmark, final int algo) {
      return this.m_data.get(benchmark).get(algo);
    }

    /** score */
    private final void score() {
      Algorithm a, b;
      double[] da, db;
      final int bms;
      int r;

      bms = this.m_benchmarks.size();

      for (final Algorithm az : this.m_algos) {
        az.m_score = 0;
        az.m_perBM = new int[bms];
      }

      for (int bem = this.m_benchmarks.size(); (--bem) >= 0;) {
        for (int algoA = this.m_algos.size(); (--algoA) > 0;) {
          a = this.m_algos.get(algoA);
          da = this.getData(bem, algoA);

          for (int algoB = algoA; (--algoB) >= 0;) {
            b = this.m_algos.get(algoB);
            db = this.getData(bem, algoB);

            final BivariateTestResult tr = MannWhitneyUTest.MANN_WHITNEY_U.test(da, db, true);

            r = tr.result(SIGNIFICANCE_LEVEL);
            if (r < 0) {
              a.m_perBM[bem]++;
              a.m_score++;
            } else {
              if (r > 0) {
                b.m_score++;
                b.m_perBM[bem]++;
              }
            }
          }

        }

      }
    }

    /**
     * print the score to a file
     * 
     * @param f
     *          the file
     */
    final void printScore(final File f) {
      final Algorithm[] list;
      int place, formerScore, bonusIdx, bonusPlace, slen, mlen;

      this.score();
      list = this.m_algos.toArray(new Algorithm[this.m_algos.size()]);
      Arrays.sort(list);

      mlen = 9;
      for (final Algorithm a : list) {
        slen = a.m_name.length();
        if (slen > mlen) {
          mlen = slen;
        }
      }

      try (final FileWriter fw = new FileWriter(f)) {
        try (final BufferedWriter bw = new BufferedWriter(fw)) {
          try {
            bw.write("algorithm");
            for (slen = 9; slen <= mlen; slen++) {
              bw.write(' ');
            }
            bw.write("place\t\tscore"); //$NON-NLS-1$
            for (String s : this.m_benchmarks) {
              bw.write('\t');
              bw.write(s);
            }
            bw.newLine();

            formerScore = Integer.MAX_VALUE;
            place = 0;
            bonusIdx = (list.length >>> 1);
            bonusPlace = -1;
            for (final Algorithm a : list) {
              bw.write(a.m_name);
              for (slen = a.m_name.length(); slen <= mlen; slen++) {
                bw.write(' ');
              }
              bw.write(' ');
              bw.write(' ');

              if (a.m_score < formerScore) {
                place++;
              }
              formerScore = a.m_score;

              if ((--bonusIdx) == 0) {
                bonusPlace = place;
              }

              bw.write(String.valueOf(place));
              bw.write(' ');
              bw.write(' ');
              bw.write('\t');
              bw.write(String.valueOf(formerScore));

              for (int y : a.m_perBM) {
                bw.write('\t');
                bw.write(String.valueOf(y));
              }

              bw.newLine();
            }

            bw.newLine();
            bw.write("Last Place to Receive Bonus: "); //$NON-NLS-1$
            bw.write(Integer.toString(bonusPlace));

          } finally {
            bw.close();
          }
        } finally {
          fw.close();
        }
      } catch (final Throwable q) {
        //
      }

    }

    /**
     * load the data from a directory
     * 
     * @param dir
     *          the directory
     */
    final void load(final File dir) {
      File[] fsA, fsB;
      File method, benchmark, data;
      int methI, benchI;

      this.m_algos.clear();
      this.m_benchmarks.clear();
      this.m_data.clear();

      if ((dir != null) && dir.exists() && dir.isDirectory()) {
        fsA = dir.listFiles();
        if (fsA != null) {
          for (final File m : fsA) {
            try {
              method = m.getCanonicalFile();
              if ((method != null) && (method.exists()) && method.isDirectory()) {
                methI = this.algoIndex(method.getName());
                if (methI >= 0) {
                  fsB = method.listFiles();

                  if (fsB != null) {
                    for (final File b : fsB) {
                      try {
                        benchmark = b.getCanonicalFile();
                        if ((benchmark != null) && (benchmark.exists()) && benchmark.isDirectory()) {
                          benchI = this.benchmarkIndex(benchmark.getName());
                          if (benchI >= 0) {
                            fsB = method.listFiles();

                            data = new File(benchmark, Evaluator.SUMMARY_FILE);
                            if (data.isFile() && data.exists()) {
                              this.setData(benchI, methI, Data.loadData(data));
                            }

                          }
                        }
                      } catch (final Throwable t2) {
                        t2.printStackTrace();
                      }
                    }

                  }
                }
              }
            } catch (final Throwable t1) {
              t1.printStackTrace();
            }
          }
        }
      }
    }

    /**
     * load the data
     * 
     * @param f
     *          the file
     * @return the data
     */
    static final double[] loadData(final File f) {
      double[] data;
      String s;
      double l;
      int i;

      data = new double[Evaluator.RUN_COUNT];
      Arrays.fill(data, Double.POSITIVE_INFINITY);
      i = 0;
      try (FileReader fr = new FileReader(f)) {
        try (BufferedReader br = new BufferedReader(fr)) {
          while ((s = br.readLine()) != null) {
            s = s.trim();
            if (s.length() > 0) {
              try {
                l = Double.parseDouble(s);
                data[i++] = l;
              } catch (final Throwable z) {/* */
              }
            }
          }
        }
      } catch (final Throwable tt) {/* */
      }
      Arrays.sort(data);
      return data;
    }
  }

  /** the algorithm class */
  static final class Algorithm implements Comparable<Algorithm> {
    /** the name */
    final String m_name;
    /** the score */
    int m_score;

    /** the per-benchmark scores */
    int[] m_perBM;

    /**
     * create
     * 
     * @param n
     *          the name
     */
    Algorithm(final String n) {
      super();
      this.m_name = n;
    }

    /** {@inheritDoc} */
    @Override
    public final int compareTo(final Algorithm a) {
      return Integer.compare(a.m_score, this.m_score);
    }
  }

  /** the empty */
  static final double[] EMPTY = new double[0];
}
