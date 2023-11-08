package de.edux.benchmark.augmentation.io;

import de.edux.augmentation.io.AugmentationImageReader;
import de.edux.augmentation.io.IAugmentationImageReader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.SECONDS)
@Timeout(time = 25, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 1)
public class AugmentatioIOBenchmark {

  IAugmentationImageReader reader;

  public AugmentatioIOBenchmark() {
    reader = new AugmentationImageReader();
  }

  public static void main(String[] args) throws Exception {

    org.openjdk.jmh.Main.main(args);
  }

  @Benchmark
  @Fork(value = 1, warmups = 1)
  @BenchmarkMode({Mode.All})
  public void readBatchOfImages() throws Exception {
    Path benchmarkDataDir =
        Paths.get(
            ".."
                + File.separator
                + "benchmark-data"
                + File.separator
                + "augmentation-benchmark-images");
    Stream<BufferedImage> imageStream = reader.readBatchOfImages(benchmarkDataDir, 1000);

    imageStream.forEach(bufferedImage -> bufferedImage.getColorModel());
  }
}
