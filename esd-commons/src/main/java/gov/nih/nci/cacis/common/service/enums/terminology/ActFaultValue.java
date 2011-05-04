/**
 * The software subject to this notice and license includes both human readable source code form and machine readable,
 * binary, object code form. The caEHR Software was developed in conjunction with the National Cancer Institute (NCI) by
 * NCI employees and 5AM Solutions Inc, SemanticBits LLC, and AgileX Technologies, Inc (collectively 'SubContractors').
 * To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United
 * States Code, section 105.
 * 
 * This caEHR Software License (the License) is between NCI and You. You (or Your) shall mean a person or an entity, and
 * all other entities that control, are controlled by, or are under common control with the entity. Control for purposes
 * of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
 * whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 * (iii) beneficial ownership of such entity.
 * 
 * This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its
 * rights in the caEHR Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly
 * display, publicly perform, and prepare derivative works of the caEHR Software; (ii) distribute and have distributed
 * to and by third parties the caEHR Software and any modifications and derivative works thereof; and (iii) sublicense
 * the foregoing rights set out in (i) and (ii) to third parties, including the right to license such rights to further
 * third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting or right of
 * payment from You or Your sub-licensees for the rights granted under this License. This License is granted at no
 * charge to You.
 * 
 * Your redistributions of the source code for the Software must retain the above copyright notice, this list of
 * conditions and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code
 * form must reproduce the above copyright notice, this list of conditions and the disclaimer of Article 6 in the
 * documentation and/or other materials provided with the distribution, if any.
 * 
 * Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: This
 * product includes software developed by the National Cancer Institute and SubContractor parties. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the Software itself, wherever such third-party
 * acknowledgments normally appear.
 * 
 * You may not use the names "The National Cancer Institute", "NCI", or any SubContractor party to endorse or promote
 * products derived from this Software. This License does not authorize You to use any trademarks, service marks, trade
 * names, logos or product names of either NCI or any of the subcontracted parties, except as required to comply with
 * the terms of this License.
 * 
 * For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs
 * and into any third party proprietary programs. However, if You incorporate the Software into third party proprietary
 * programs, You agree that You are solely responsible for obtaining any permission from such third parties required to
 * incorporate the Software into such third party proprietary programs and for informing Your sub-licensees, including
 * without limitation Your end-users, of their obligation to secure any required permissions from such third parties
 * before incorporating the Software into such third party proprietary software programs. In the event that You fail to
 * obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to the
 * extent prohibited by law, resulting from Your failure to obtain such permissions.
 * 
 * For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and
 * to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses
 * of modifications of the Software, or any derivative works of the Software as a whole, provided Your use,
 * reproduction, and distribution of the Work otherwise complies with the conditions stated in this License.
 * 
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, ANY OF ITS SUBCONTRACTED PARTIES OR THEIR AFFILIATES BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.cacis.common.service.enums.terminology;

/**
 * @author ramakrishnanr
 * @since Nov 9, 2010
 * 
 */
public enum ActFaultValue implements CdEnum {
    /**
     * Fatal
     */
    CRE10100("CRE10100", "One of the dependent systems of Referred To provider system is "
            + "unavailable momentarily and hence the given operation failed"),

            /****************************************************
             * Codes 100XX - belong to general Service Exceptions
             ****************************************************/

            /**
             * Fatal - CRE10000 service exception code
             */
            CRE10000("CRE10000", "General service exception occured"),

            /**
             * Fatal - CRE10001 service exception code
             */
            CRE10001("CRE10001", "Schematron validation failed exception occured"),

            /**
             * Major
             */
            CRE10101("CRE10101", "Invalid ReferralOrder representation"),

            /**
             * Major
             */
            CRE10102("CRE10102", "Ambiguous ReferralOrder (multiple hits)"),

            /**
             * Major
             */
            CRE10103("CRE10103", "No referral result documents found"),

            /**
             * Minor
             */
            CRE10104("CRE10104", "Referral result documents not ready yet (statusCode other than 'Completed')"),

            /**
             * Minor
             */
            CRE10105("CRE10105", "Null statusCode"),

            /**
             * Minor
             */
            CRE10106("CRE10106", "Invalid statusCode"),

            /**
             * Minor
             */
            CRE10107("CRE10107", "Invalid order state transition."),

            /**
             * Minor
             */
            CRE10108("CRE10108", "Referral rejected"),

            /**
             * Minor
             */
            CRE10109("CRE10109", "Referral not found"),

            /**
             * Minor
             */
            CRE10110("CRE10110", "Invalid query - missing identifier"),

            /**
             * Fatal - One of the dependent systems of Referred To provider system is unavailable
             * momentarily and hence the given operation failed
             */
            _40000083(
                    "40000083",
                    "One of the dependent systems of Referred To provider system is unavailable momentarily " +
            "and hence the given operation failed"),

            /**
             * Major - Invalid ReferralOrder representation
             */
            _20000084("20000084", "Invalid ReferralOrder representation"),

            /**
             * Major - Ambiguous ReferralOrder (multiple hits)
             */
            _60000085("60000085", "Ambiguous ReferralOrder (multiple hits)"),

            /**
             * Major - No referral result documents found
             */
            _90000086("90000086", "No referral result documents found"),

            /**
             * Minor - Referral result documents not ready yet (statusCode other than 'Completed')
             */
            _50000087("50000087", "Referral result documents not ready yet (statusCode other than 'Completed')"),

            /**
             * Minor - Null statusCode
             */
            _30000088("30000088", "Null statusCode"),

            /**
             * Minor - Invalid statusCode
             */
            _10000089("10000089", "Invalid statusCode"),

            /**
             * Minor - Invalid order state transition.
             */
            _50000090("50000090", "Invalid order state transition."),

            /**
             * Minor - Referral rejected
             */
            _20000091("20000091", "Referral rejected"),

            /**
             * Minor - Referral not found
             */
            _30000092("30000092", "Referral not found"),

            /**
             * Minor - Invalid query - missing identifier
             */
            _90000093("90000093", "Invalid query - missing identifier"),

            /**
             * 
             */
            _20000017("20000017", "Cannot process Patient Person informational CMET"),

            /**
             * 
             */
            _50000014("50000014", "Cannot process Person CMET"),

            /**
             * 
             */
            _30000015("30000015", "Cannot process Referral CMET"),

            /**
             * 
             */
            _60000018("60000018", "Cannot process Allergy Concern CMET");

    private final String code;
    private final String displayName;

    /**
     * Value Set Comment (from terminology sheet):
     * "Describes the sub type of the code that is specified for this fault and ActFaultCode.
     * These codes will be dependent on local enumerations of fault types. For example, a
     * service may specify and enumerate a fault such as 0001 "
     * System could not process Person CMET", where 0001 is the code."
     */
    private static final String VALUE_SET = "2.16.840.1.113883.13.86";
    private static final String VALUE_SET_VERSION = "8/30/2010";

    /**
     * constructor..
     * 
     * @param code code
     * @param displayName displayName
     */
    private ActFaultValue(final String code, final String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCodeSystem() {
        return CodeSystem.ACT_FAULT_VALUE.getCodeSystem();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCodeSystemName() {
        return CodeSystem.ACT_FAULT_VALUE.getCodeSystemName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCodeSystemVersion() {
        return CodeSystem.ACT_FAULT_VALUE.getCodeSystemVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValueSet() {
        return VALUE_SET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValueSetVersion() {
        return VALUE_SET_VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCode() {
        return this.code;
    }

}
