public class Time {
    int days = 1;
    int weeks = 1;
    int month = 1;

    public void timeChange() {
        days++;
        if (days % 7 == 0) {
            weeks++;
            if (weeks % 4 == 0) {
                month++;
                weeks = 1;
            }
            days = 1;
        }
    }
}
