package remix.util.math;

import remix.util.time.ElapsedTimer;

import remix.compat.vecmath.Point2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public final class ConvexHull2DHelper {
    public List<Point2d> buildConvexHull(
            List<Point2d> list) {
        list.sort(
                Comparator.comparingDouble(
                        point2d -> point2d.x));
        int i = list.size();
        Point2d[] point2dArray =
                new Point2d[i];
        point2dArray[0] = (Point2d) list.get(0);
        ElapsedTimer.fM();

        point2dArray[1] = (Point2d) list.get(1);
        int j = 2;

        int k = 2;

        while (k < i) {
            point2dArray[j] = (Point2d) list.get(k);
            j++;

            while (true) {
                if (j > 2
                        && !method_73d016fe2d5f8b3adbc7(
                                point2dArray[j - 3],
                                point2dArray[j - 2],
                                point2dArray[j - 1])) {
                    point2dArray[j - 2] = point2dArray[j - 1];
                    j--;

                    continue;
                }

                k++;

                break;
            }
        }

        Point2d[] point2dArrayValue =
                new Point2d[i];
        point2dArrayValue[0] = (Point2d) list.get(i - 1);
        point2dArrayValue[1] = (Point2d) list.get(i - 2);
        int l = 2;
        int i1 = i - 3;

        while (i1 >= 0) {
            point2dArrayValue[l] = (Point2d) list.get(i1);
            l++;

            while (true) {
                if (l > 2
                        && !method_73d016fe2d5f8b3adbc7(
                                point2dArrayValue[l - 3],
                                point2dArrayValue[l - 2],
                                point2dArrayValue[l - 1])) {
                    point2dArrayValue[l - 2] = point2dArrayValue[l - 1];
                    l--;

                    continue;
                }

                i1--;

                break;
            }
        }

        ArrayList arrayList =
                new ArrayList<>(Arrays.asList(point2dArray).subList(0, j));
        arrayList.addAll(Arrays.asList(point2dArrayValue).subList(1, l - 1));
        return arrayList;
    }

    private static boolean method_73d016fe2d5f8b3adbc7(
            Point2d point2d,
            Point2d point2dValue,
            Point2d point2dValue2) {
        return (point2dValue.x - point2d.x)
                                * (point2dValue2.y - point2d.y)
                        - (point2dValue.y - point2d.y)
                                * (point2dValue2.x - point2d.x)
                > 0.0;
    }
}
