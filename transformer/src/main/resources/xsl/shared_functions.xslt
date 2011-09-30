<xsl:stylesheet version="1.1" xmlns="urn:hl7-org:v3" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:cda="urn:hl7-org:v3" xmlns:trim="urn:tolven-org:trim:4.0" exclude-result-prefixes="xs cda trim">
	<!-- find and return standard code mapping -->
	<xsl:template name="translateCode">
		<xsl:param name="trim_dispNm"/>
		<xsl:param name="overriddenOriginalText"/>
		<!-- if noXSI has any value,  then don't creat attribute xsi:type='CD' -->
		<xsl:param name="noXSI"/>
		<xsl:variable name="normalizedDisplayName" select="normalize-space($trim_dispNm)"/>
		<xsl:variable name="lowercase" select="'abcdefghijklmnopqrstuvwxyz'"/>
		<xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'"/>
		<!-- This is case insenstive, the use of translate() is to keep the transform 1.0, rather than using 2.0's lower-case and upper-case functions.  -->
		<xsl:variable name="targetCode" select="document('TRIM-codes.xml')/translation//code[@displayName= translate($normalizedDisplayName, $uppercase, $lowercase)]/translate"/>
		<xsl:choose>
			<xsl:when test="string-length($noXSI)>0"/>
			<xsl:otherwise>
				<xsl:attribute name="xsi:type">CD</xsl:attribute>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:choose>
			<xsl:when test="$targetCode">
				<xsl:if test="$targetCode/@code and $targetCode/@code != ''">
					<xsl:attribute name="code"><xsl:value-of select="$targetCode/@code"/></xsl:attribute>
				</xsl:if>
				<xsl:if test="$targetCode/@codeSystem and $targetCode/@codeSystem != ''">
					<xsl:attribute name="codeSystem"><xsl:value-of select="$targetCode/@codeSystem"/></xsl:attribute>
				</xsl:if>
				<xsl:if test="$targetCode/@codeSystemName and $targetCode/@codeSystemName != ''">
					<xsl:attribute name="codeSystemName"><xsl:value-of select="$targetCode/@codeSystemName"/></xsl:attribute>
				</xsl:if>
				<xsl:if test="$targetCode/@displayName != '' and $targetCode/@displayName != ''">
					<xsl:attribute name="displayName"><xsl:value-of select="$targetCode/@displayName"/></xsl:attribute>
				</xsl:if>
			</xsl:when>
			<xsl:otherwise>
				<xsl:attribute name="nullFlavor">OTH</xsl:attribute>
			</xsl:otherwise>
		</xsl:choose>
		<originalText>
			<xsl:choose>
				<!-- specifically for breast cancer history cancer diagnosis related trim displayName -->
				<xsl:when test="$overriddenOriginalText and $overriddenOriginalText != ''">
					<xsl:value-of select="$overriddenOriginalText"/>
				</xsl:when>
				<xsl:when test="starts-with($normalizedDisplayName,'Breast Cancer History:')">
					<xsl:variable name="newOrig" select="substring-after($normalizedDisplayName,'History:')"/>
					<xsl:value-of select="$newOrig"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$normalizedDisplayName"/>
				</xsl:otherwise>
			</xsl:choose>
		</originalText>
	</xsl:template>
	<!-- find and return negationInd value -->
	<xsl:template name="getNegationInd">
		<xsl:param name="trim_dispNm"/>
		<xsl:variable name="lowercase" select="'abcdefghijklmnopqrstuvwxyz'"/>
		<xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'"/>
		<xsl:variable name="targetCode" select="document('TRIM-codes.xml')/translation//code[@displayName= translate(normalize-space($trim_dispNm), $uppercase, $lowercase)]/translate"/>
		<xsl:choose>
			<xsl:when test="$targetCode/@negationInd='true'">
				<xsl:value-of select="string('true')"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="string('false')"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="translateValueSet">
		<xsl:param name="trim_valueSet"/>
		<xsl:param name="trim_dispNm"/>
		<xsl:param name="elementType" select="'value'"/>
		<xsl:param name="xsiType" select="'CD'"/>
		<xsl:variable name="lowercase" select="'abcdefghijklmnopqrstuvwxyz'"/>
		<xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'"/>
		<!-- This is case insenstive, the use of translate() is to keep the transform 1.0, rather than using 2.0's lower-case and upper-case functions.  -->
		<xsl:variable name="targetCode" select="document('TRIM-codes.xml')/translation/valueset[not($trim_valueSet) or @name=$trim_valueSet]/code[translate(@displayName, $uppercase, $lowercase)=translate($trim_dispNm, $uppercase, $lowercase)]/translate"/>
		<xsl:element name="{$elementType}" namespace="urn:hl7-org:v3">
			<xsl:attribute name="xsi:type"><xsl:value-of select="$xsiType"/></xsl:attribute>
			<xsl:choose>
				<xsl:when test="$targetCode">
					<xsl:attribute name="code"><xsl:value-of select="$targetCode/@code"/></xsl:attribute>
					<xsl:attribute name="codeSystem"><xsl:value-of select="$targetCode/@codeSystem"/></xsl:attribute>
					<xsl:attribute name="codeSystemName"><xsl:value-of select="$targetCode/@codeSystemName"/></xsl:attribute>
					<xsl:attribute name="displayName"><xsl:value-of select="$targetCode/@displayName"/></xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:attribute name="nullFlavor">OTH</xsl:attribute>
				</xsl:otherwise>
			</xsl:choose>
			<originalText>
				<xsl:value-of select="$trim_dispNm"/>
			</originalText>
		</xsl:element>
	</xsl:template>
	<xsl:template name="make_II">
		<xsl:param name="II_root"/>
		<xsl:param name="II_ext"/>
		<xsl:param name="II_assAuth"/>
		<xsl:choose>
			<xsl:when test="string-length($II_root)>0">
				<id>
					<xsl:attribute name="root"><xsl:value-of select="$II_root"/></xsl:attribute>
					<xsl:if test="string-length($II_ext)>0">
						<xsl:attribute name="extension"><xsl:value-of select="$II_ext"/></xsl:attribute>
					</xsl:if>
					<xsl:if test="string-length($II_assAuth)>0">
						<xsl:attribute name="assigningAuthorityName"><xsl:value-of select="$II_assAuth"/></xsl:attribute>
					</xsl:if>
				</id>
			</xsl:when>
			<xsl:otherwise>
				<id nullFlavor="NI"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="make_code_CD">
		<xsl:param name="CD_code"/>
		<xsl:param name="CD_dispNm"/>
		<xsl:param name="CD_codeSys"/>
		<xsl:param name="CD_codeSysNm"/>
		<xsl:choose>
			<xsl:when test="string-length($CD_code)>0">
				<code xmlns="urn:hl7-org:v3">
					<xsl:attribute name="code"><xsl:value-of select="$CD_code"/></xsl:attribute>
					<xsl:if test="string-length($CD_dispNm)>0">
						<xsl:attribute name="displayName"><xsl:value-of select="$CD_dispNm"/></xsl:attribute>
					</xsl:if>
					<xsl:if test="string-length($CD_codeSys)>0">
						<xsl:attribute name="codeSystem"><xsl:value-of select="$CD_codeSys"/></xsl:attribute>
					</xsl:if>
					<xsl:if test="string-length($CD_codeSysNm)>0">
						<xsl:attribute name="codeSystemName"><xsl:value-of select="$CD_codeSysNm"/></xsl:attribute>
					</xsl:if>
				</code>
			</xsl:when>
			<xsl:otherwise>
				<code xmlns="urn:hl7-org:v3" nullFlavor="NI"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="make_name">
		<xsl:param name="trimNm"/>
		<name xmlns="urn:hl7-org:v3">
			<xsl:if test="string-length($trimNm/trim:EN/trim:use)">
				<xsl:attribute name="use"><xsl:value-of select="$trimNm/trim:EN/trim:use"/></xsl:attribute>
			</xsl:if>
			<xsl:for-each select="$trimNm/trim:EN/trim:part">
				<xsl:if test="trim:type='FAM'">
					<family>
						<xsl:value-of select="trim:ST"/>
					</family>
				</xsl:if>
				<xsl:if test="trim:type='GIV'">
					<given>
						<xsl:value-of select="trim:ST"/>
					</given>
				</xsl:if>
			</xsl:for-each>
		</name>
	</xsl:template>
	<xsl:template name="string-replace-all">
		<xsl:param name="text"/>
		<xsl:param name="replace"/>
		<xsl:param name="by"/>
		<xsl:choose>
			<xsl:when test="contains($text,$replace)">
				<xsl:value-of select="substring-before($text,$replace)"/>
				<xsl:value-of select="$by"/>
				<xsl:call-template name="string-replace-all">
					<xsl:with-param name="text" select="substring-after($text,$replace)"/>
					<xsl:with-param name="replace" select="$replace"/>
					<xsl:with-param name="by" select="$by"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$text"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="translateOtherValueInCD">
		<xsl:param name="origTxt"/>
		<value>
			<xsl:attribute name="xsi:type">CD</xsl:attribute>
			<xsl:attribute name="nullFlavor">OTH</xsl:attribute>
			<originalText>
				<xsl:value-of select="$origTxt"/>
			</originalText>
		</value>
	</xsl:template>
	<xsl:template name="translateUnknownValueInCD">
		<value>
			<xsl:attribute name="xsi:type">CD</xsl:attribute>
			<xsl:attribute name="nullFlavor">UNK</xsl:attribute>
		</value>
	</xsl:template>
	<xsl:template name="mapPerformer">
		<xsl:param name="trim_participation"/>
		<xsl:param name="trim_ST"/>
		<xsl:variable name="trim_player" select="$trim_participation/trim:role/trim:player"/>
		<xsl:choose>
			<xsl:when test="$trim_participation">
				<performer typeCode="PRF">
					<assignedEntity>
						<id>
							<xsl:if test="$trim_participation/trim:role/trim:id/trim:II[trim:extension = '' or trim:extension = 'Select' or trim:root = '']">
								<xsl:attribute name="nullFlavor">NI</xsl:attribute>
							</xsl:if>
							<xsl:if test="$trim_participation/trim:role/trim:id/trim:II[trim:extension != '' and trim:extension != 'Select']">
								<xsl:attribute name="extension"><xsl:value-of select="$trim_participation/trim:role/trim:id/trim:II/trim:extension"/></xsl:attribute>
							</xsl:if>
							<xsl:if test="$trim_participation/trim:role/trim:id/trim:II/trim:root != ''">
								<xsl:attribute name="root"><xsl:value-of select="$trim_participation/trim:role/trim:id/trim:II/trim:root"/></xsl:attribute>
							</xsl:if>
						</id>
						<addr nullFlavor="NI"/>
						<telecom nullFlavor="NI"/>
						<xsl:if test="string-length($trim_player/trim:name/trim:EN/trim:part[contains(trim:label, 'Other')]/trim:ST)>0">
							<assignedPerson>
								<name>
									<xsl:if test="$trim_player/trim:name/trim:EN[trim:use != '']">
										<xsl:attribute name="use"><xsl:value-of select="$trim_player/trim:name/trim:EN/trim:use"/></xsl:attribute>
									</xsl:if>
									<xsl:choose>
										<xsl:when test="$trim_player/trim:name/trim:EN/trim:part[contains(trim:label, 'Other')]">
											<xsl:value-of select="$trim_player/trim:name/trim:EN/trim:part[contains(trim:label, 'Other')]/trim:ST"/>
										</xsl:when>
										<xsl:otherwise>
											<xsl:for-each select="$trim_player/trim:name/trim:EN/trim:part">
												<xsl:variable name="partElementName">
													<xsl:choose>
														<xsl:when test="trim:type = 'GIV'">given</xsl:when>
														<xsl:when test="trim:type = 'FAM'">family</xsl:when>
													</xsl:choose>
												</xsl:variable>
												<xsl:element name="{$partElementName}">
													<xsl:value-of select="trim:ST"/>
												</xsl:element>
											</xsl:for-each>
										</xsl:otherwise>
									</xsl:choose>
								</name>
							</assignedPerson>
						</xsl:if>
					</assignedEntity>
				</performer>
			</xsl:when>
			<xsl:when test="$trim_ST">
				<performer>
					<assignedEntity>
						<id nullFlavor="NI"/>
						<addr nullFlavor="NI"/>
						<telecom nullFlavor="NI"/>
						<assignedPerson>
							<name use="L">
								<xsl:value-of select="$trim_ST"/>
							</name>
						</assignedPerson>
					</assignedEntity>
				</performer>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<!--convert mm/dd/yyyy to CDA dateTime format-->
	<xsl:template name="format-date">
		<xsl:param name="theDate"/>
		<xsl:variable name="monthValue" select="substring-before($theDate, '/')"/>
		<xsl:variable name="monthLeftover" select="substring($theDate, string-length($monthValue)+2)"/>
		<xsl:variable name="dateValue" select="substring-before($monthLeftover, '/')"/>
		<xsl:variable name="dateLeftover" select="substring($monthLeftover, string-length($dateValue)+2)"/>
		<xsl:variable name="yearValue" select="substring($dateLeftover, 1)"/>
		<xsl:value-of select="$yearValue"/>
		<xsl:if test="string-length($monthValue) = 1">0</xsl:if>
		<xsl:value-of select="$monthValue"/>
		<xsl:if test="string-length($dateValue) = 1">0</xsl:if>
		<xsl:value-of select="$dateValue"/>
	</xsl:template>
	<xsl:template name="make_effectiveTime_lowHigh">
		<xsl:param name="lowTm"/>
		<xsl:param name="highTm"/>
		<effectiveTime>
			<low>
				<xsl:choose>
					<xsl:when test="string-length($lowTm)>0">
						<xsl:attribute name="value"><xsl:value-of select="$lowTm"/></xsl:attribute>
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="nullFlavor"><xsl:value-of select="'UNK'"/></xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
			</low>
			<xsl:choose>
				<xsl:when test="string-length($highTm)>0">
					<high>
						<xsl:attribute name="value"><xsl:value-of select="$highTm"/></xsl:attribute>
					</high>
				</xsl:when>
			</xsl:choose>
		</effectiveTime>
	</xsl:template>
	<!--get label based on displayName-->
	<xsl:template name="getLabel">
		<xsl:param name="trimNode"/>
		<xsl:param name="vsName"/>
		<xsl:param name="dispNm"/>
		<xsl:choose>
			<xsl:when test="$trimNode/trim:valueSet[@name=$vsName]/trim:CE[trim:displayName=$dispNm]/trim:label">
				<xsl:variable name="lblTxt" select="$trimNode/trim:valueSet[@name=$vsName]/trim:CE[trim:displayName=$dispNm]/trim:label"/>
				<xsl:if test="string-length($lblTxt)>0">
					<xsl:value-of select="$lblTxt"/>
				</xsl:if>
				<xsl:if test="string-length($lblTxt) &lt; 1">
					<xsl:value-of select="$dispNm"/>
				</xsl:if>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$dispNm"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
