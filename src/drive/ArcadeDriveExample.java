package drive;

public class ArcadeDriveExample {

	// expected output left:-1 right 1;
	private static double fakeInputX=-.9;
	private static double fakeInputY=.9;

	private static final double DEADZONEX=.05;
	private static final double DEADZONEY=.05;

	public static void main(String[] args) {
		// Variables which store motor output to pass to the robot later
		double outputLeftMotor=0;
		double outputRightMotor=0;

		// variables to store the max sum and difference
		double max;
		double sum;
		double difference;

		// If the stick is within the x dead zone set the x input to 0
		if (fakeInputX<DEADZONEX&&fakeInputX>-1*DEADZONEX) {
			fakeInputX=0;
		}

		// if the stick is within the y dead zone set y
		if (fakeInputY<DEADZONEY&&fakeInputY>-1*DEADZONEY) {
			fakeInputY=0;
		}

		// here I set the max
		max=Math.abs(fakeInputX);

		if (Math.abs(fakeInputY)>max) {
			max=Math.abs(fakeInputY);
		}

		// here I set the sum
		sum=fakeInputX+fakeInputY;

		// here I set the difference
		difference=fakeInputX-fakeInputY;

		// what we do if we have input
		if (fakeInputX>0||fakeInputY>0||fakeInputX<0||fakeInputY<0) {

			if (fakeInputY>=0) {
				// check if in first quadrant
				if (fakeInputX>=0) {

					outputLeftMotor=max;
					outputRightMotor=-difference;
				}

				// joystick is in quadrant 2
				else {
					outputLeftMotor=difference;
					outputRightMotor=max;
				}
			}

			else {
				// check if in quadrant 4
				if (fakeInputX>=0) {

					outputLeftMotor=sum;
					outputRightMotor=-max;
				}

				// joystick is in quadrant 3
				else {
					outputLeftMotor=-max;
					outputRightMotor=-sum;
				}

			}

			// this is where we would pass the motor output to the robot if
			// there is input
			System.out.println("Left motor: "+outputLeftMotor+" Right Motor: "+outputRightMotor);

		}

		// what we do if we have zero input
		else {
			// this is where we would pass the motor output to the robot if
			// there is no input
			System.out.println("Left motor: "+outputLeftMotor+" Right Motor: "+outputRightMotor);
		}

	}

}
