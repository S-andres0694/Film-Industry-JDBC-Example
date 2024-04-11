/**
 * Awards is the class which contains all the values for the columns of the Awards table. These
 * values are retrieved through a series of methods in the Parsers class, and they are also inputted
 * to the actual database using methods in the PopulateDB class
 *
 * @author 230018374
 */
public class Awards {
  private final String awardInstitution;
  private final String motive;

  /**
   * Initialises each one of the different Award objects, which correspond to an entry in the Awards
   * table.
   *
   * @param awardInstitution the institution which gives the award
   * @param motive the reason that explains why this award was given
   */
  public Awards(String awardInstitution, String motive) {
    this.awardInstitution = awardInstitution;
    this.motive = motive;
  }

  /**
   * Standard getter for the institution which gives the award
   *
   * @return the award institution in the form of a string
   */
  public String getAwardInstitution() {
    return awardInstitution;
  }

  /**
   * Standard getter for the award's motive
   *
   * @return the motive of the award as a string
   */
  public String getMotive() {
    return motive;
  }
}
