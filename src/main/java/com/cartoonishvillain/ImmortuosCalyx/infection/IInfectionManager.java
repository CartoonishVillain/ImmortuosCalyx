package com.cartoonishvillain.ImmortuosCalyx.infection;

public interface IInfectionManager {
     int getInfectionProgress();
     void setInfectionProgress(int infectionProgress);
     void setInfectionProgressIfLower(int infectionProgress);
     void addInfectionProgress(int infectionProgress);
     int getInfectionTimer();
     void addInfectionTimer(int Time);
     void setInfectionTimer(int Time);
     double getResistance();
     void addResistance(double resistance);
     void setResistance(double resistance);
     void setFollower(boolean isFollower);
     boolean isFollower();
}
