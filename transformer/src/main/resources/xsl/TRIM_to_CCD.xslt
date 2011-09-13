<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:ns0="urn:tolven-org:trim:4.0" exclude-result-prefixes="xs fn ns0">
	<xsl:output method="xml" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/">
		<ClinicalDocument xmlns="urn:hl7-org:v3">
			<xsl:attribute name="xsi:schemaLocation" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'urn:hl7-org:v3 CDA.xsd'"/>
			<xsl:for-each select="ns0:trim">
				<xsl:variable name="var1_act" as="node()?" select="ns0:act"/>
				<xsl:variable name="var2_resultof_create_attribute" as="node()">
					<xsl:attribute name="codeSystem" namespace="" select="'2.16.840.1.113883.6.1'"/>
				</xsl:variable>
				<xsl:variable name="var3_resultof_create_attribute" as="node()">
					<xsl:attribute name="codeSystemName" namespace="" select="'LOINC'"/>
				</xsl:variable>
				<realmCode>
					<xsl:attribute name="code" namespace="" select="'US'"/>
				</realmCode>
				<typeId>
					<xsl:attribute name="root" namespace="" select="'2.16.840.1.113883.1.3'"/>
					<xsl:attribute name="extension" namespace="" select="'POCD_HD000040'"/>
				</typeId>
				<templateId>
					<xsl:attribute name="root" namespace="" select="'2.16.840.1.113883.10.20.1'"/>
				</templateId>
				<templateId>
					<xsl:attribute name="root" namespace="" select="'2.16.840.1.113883.3.88.11.32.1'"/>
				</templateId>
				<templateId>
					<xsl:attribute name="root" namespace="" select="'1.3.6.1.4.1.19376.1.5.3.1.1.1'"/>
				</templateId>
				<templateId>
					<xsl:attribute name="root" namespace="" select="'2.16.840.1.113883.10.20.3'"/>
				</templateId>
				<xsl:for-each select="$var1_act/ns0:id">
					<xsl:variable name="var4_II" as="node()*" select="ns0:II"/>
					<id>
						<xsl:for-each select="$var4_II/ns0:root">
							<xsl:attribute name="root" namespace="" select="fn:string(.)"/>
						</xsl:for-each>
						<xsl:for-each select="$var4_II/ns0:extension">
							<xsl:attribute name="extension" namespace="" select="fn:string(.)"/>
						</xsl:for-each>
						<xsl:for-each select="$var4_II/ns0:assigningAuthorityName">
							<xsl:attribute name="assigningAuthorityName" namespace="" select="fn:string(.)"/>
						</xsl:for-each>
						<xsl:for-each select="$var4_II/ns0:displayable">
							<xsl:attribute name="displayable" namespace="" select="xs:string(xs:boolean(fn:string(.)))"/>
						</xsl:for-each>
					</id>
				</xsl:for-each>
				<xsl:for-each select="$var1_act/ns0:code">
					<xsl:variable name="var7_cur" as="node()" select="."/>
					<xsl:for-each select="ns0:CD">
						<code>
							<xsl:for-each select="ns0:code">
								<xsl:attribute name="code" namespace="" select="fn:string(.)"/>
							</xsl:for-each>
							<xsl:for-each select="ns0:codeSystem">
								<xsl:attribute name="codeSystem" namespace="" select="fn:string(.)"/>
							</xsl:for-each>
							<xsl:for-each select="ns0:codeSystemName">
								<xsl:attribute name="codeSystemName" namespace="" select="fn:string(.)"/>
							</xsl:for-each>
							<xsl:for-each select="ns0:codeSystemVersion">
								<xsl:attribute name="codeSystemVersion" namespace="" select="fn:string(.)"/>
							</xsl:for-each>
							<xsl:for-each select="ns0:displayName">
								<xsl:attribute name="displayName" namespace="" select="fn:string(.)"/>
							</xsl:for-each>
							<xsl:for-each select="$var7_cur/ns0:originalText">
								<originalText>
									<xsl:sequence select="fn:string(.)"/>
								</originalText>
							</xsl:for-each>
							<xsl:for-each select="ns0:qualifier">
								<xsl:variable name="var5_inverted" as="node()?" select="@inverted"/>
								<qualifier>
									<xsl:if test="fn:exists($var5_inverted)">
										<xsl:attribute name="inverted" namespace="" select="xs:string(xs:boolean(fn:string($var5_inverted)))"/>
									</xsl:if>
									<xsl:for-each select="ns0:name">
										<name>
											<xsl:for-each select="ns0:code">
												<xsl:attribute name="code" namespace="" select="fn:string(.)"/>
											</xsl:for-each>
											<xsl:for-each select="ns0:codeSystem">
												<xsl:attribute name="codeSystem" namespace="" select="fn:string(.)"/>
											</xsl:for-each>
											<xsl:for-each select="ns0:codeSystemName">
												<xsl:attribute name="codeSystemName" namespace="" select="fn:string(.)"/>
											</xsl:for-each>
											<xsl:for-each select="ns0:codeSystemVersion">
												<xsl:attribute name="codeSystemVersion" namespace="" select="fn:string(.)"/>
											</xsl:for-each>
											<xsl:for-each select="ns0:displayName">
												<xsl:attribute name="displayName" namespace="" select="fn:string(.)"/>
											</xsl:for-each>
											<xsl:for-each select="ns0:originalText">
												<originalText>
													<xsl:sequence select="fn:string(.)"/>
												</originalText>
											</xsl:for-each>
										</name>
									</xsl:for-each>
									<xsl:for-each select="ns0:value">
										<value>
											<xsl:for-each select="ns0:originalText">
												<originalText>
													<xsl:sequence select="fn:string(.)"/>
												</originalText>
											</xsl:for-each>
										</value>
									</xsl:for-each>
								</qualifier>
							</xsl:for-each>
							<xsl:for-each select="ns0:translation">
								<translation>
									<xsl:for-each select="ns0:code">
										<xsl:attribute name="code" namespace="" select="fn:string(.)"/>
									</xsl:for-each>
									<xsl:for-each select="ns0:codeSystem">
										<xsl:attribute name="codeSystem" namespace="" select="fn:string(.)"/>
									</xsl:for-each>
									<xsl:for-each select="ns0:codeSystemName">
										<xsl:attribute name="codeSystemName" namespace="" select="fn:string(.)"/>
									</xsl:for-each>
									<xsl:for-each select="ns0:codeSystemVersion">
										<xsl:attribute name="codeSystemVersion" namespace="" select="fn:string(.)"/>
									</xsl:for-each>
									<xsl:for-each select="ns0:displayName">
										<xsl:attribute name="displayName" namespace="" select="fn:string(.)"/>
									</xsl:for-each>
									<xsl:for-each select="ns0:originalText">
										<originalText>
											<xsl:sequence select="fn:string(.)"/>
										</originalText>
									</xsl:for-each>
									<xsl:for-each select="ns0:qualifier">
										<xsl:variable name="var6_inverted" as="node()?" select="@inverted"/>
										<qualifier>
											<xsl:if test="fn:exists($var6_inverted)">
												<xsl:attribute name="inverted" namespace="" select="xs:string(xs:boolean(fn:string($var6_inverted)))"/>
											</xsl:if>
											<xsl:for-each select="ns0:name">
												<name>
													<xsl:for-each select="ns0:code">
														<xsl:attribute name="code" namespace="" select="fn:string(.)"/>
													</xsl:for-each>
													<xsl:for-each select="ns0:codeSystem">
														<xsl:attribute name="codeSystem" namespace="" select="fn:string(.)"/>
													</xsl:for-each>
													<xsl:for-each select="ns0:codeSystemName">
														<xsl:attribute name="codeSystemName" namespace="" select="fn:string(.)"/>
													</xsl:for-each>
													<xsl:for-each select="ns0:codeSystemVersion">
														<xsl:attribute name="codeSystemVersion" namespace="" select="fn:string(.)"/>
													</xsl:for-each>
													<xsl:for-each select="ns0:displayName">
														<xsl:attribute name="displayName" namespace="" select="fn:string(.)"/>
													</xsl:for-each>
													<xsl:for-each select="ns0:originalText">
														<originalText>
															<xsl:sequence select="fn:string(.)"/>
														</originalText>
													</xsl:for-each>
												</name>
											</xsl:for-each>
											<xsl:for-each select="ns0:value">
												<value>
													<xsl:for-each select="ns0:originalText">
														<originalText>
															<xsl:sequence select="fn:string(.)"/>
														</originalText>
													</xsl:for-each>
												</value>
											</xsl:for-each>
										</qualifier>
									</xsl:for-each>
									<xsl:for-each select="ns0:translation">
										<translation>
											<xsl:sequence select="()"/>
										</translation>
									</xsl:for-each>
								</translation>
							</xsl:for-each>
						</code>
					</xsl:for-each>
				</xsl:for-each>
				<xsl:for-each select="$var1_act/ns0:title/ns0:ST">
					<xsl:variable name="var8_language" as="node()?" select="@language"/>
					<title>
						<xsl:if test="fn:exists($var8_language)">
							<xsl:attribute name="language" namespace="" select="fn:string($var8_language)"/>
						</xsl:if>
						<xsl:sequence select="fn:string(.)"/>
					</title>
				</xsl:for-each>
				<xsl:for-each select="$var1_act/ns0:effectiveTime/ns0:TS">
					<effectiveTime>
						<xsl:for-each select="ns0:value">
							<xsl:attribute name="value" namespace="" select="fn:string(.)"/>
						</xsl:for-each>
					</effectiveTime>
				</xsl:for-each>
				<confidentialityCode>
					<xsl:attribute name="code" namespace="" select="'N'"/>
				</confidentialityCode>
				<xsl:for-each select="$var1_act/ns0:participation[(fn:string(@typeCode) = 'SBJ')]">
					<xsl:variable name="var9_role" as="node()?" select="ns0:role"/>
					<recordTarget>
						<patientRole>
							<xsl:for-each select="$var9_role/ns0:id/ns0:II">
								<id>
									<xsl:for-each select="ns0:root">
										<xsl:attribute name="root" namespace="" select="fn:string(.)"/>
									</xsl:for-each>
									<xsl:for-each select="ns0:extension">
										<xsl:attribute name="extension" namespace="" select="fn:string(.)"/>
									</xsl:for-each>
									<xsl:for-each select="ns0:assigningAuthorityName">
										<xsl:attribute name="assigningAuthorityName" namespace="" select="fn:string(.)"/>
									</xsl:for-each>
									<xsl:for-each select="ns0:displayable">
										<xsl:attribute name="displayable" namespace="" select="xs:string(xs:boolean(fn:string(.)))"/>
									</xsl:for-each>
								</id>
							</xsl:for-each>
							<patient>
								<name>
									<xsl:for-each select="$var9_role/ns0:player/ns0:name/ns0:EN/ns0:use">
										<xsl:attribute name="use" namespace="" select="fn:string(.)"/>
									</xsl:for-each>
									<xsl:for-each select="$var9_role/ns0:player/ns0:name/ns0:EN/ns0:part">
										<xsl:variable name="var10_result" as="xs:boolean?">
											<xsl:for-each select="ns0:type">
												<xsl:sequence select="(fn:string(.) = 'FAM')"/>
											</xsl:for-each>
										</xsl:variable>
										<xsl:if test="fn:exists($var10_result[.])">
											<family>
												<xsl:for-each select="ns0:ST">
													<xsl:sequence select="fn:string(.)"/>
												</xsl:for-each>
											</family>
										</xsl:if>
									</xsl:for-each>
									<xsl:for-each select="$var9_role/ns0:player/ns0:name/ns0:EN/ns0:part">
										<xsl:variable name="var11_result" as="xs:boolean?">
											<xsl:for-each select="ns0:type">
												<xsl:sequence select="(fn:string(.) = 'GIV')"/>
											</xsl:for-each>
										</xsl:variable>
										<xsl:if test="fn:exists($var11_result[.])">
											<given>
												<xsl:for-each select="ns0:ST">
													<xsl:sequence select="fn:string(.)"/>
												</xsl:for-each>
											</given>
										</xsl:if>
									</xsl:for-each>
								</name>
							</patient>
						</patientRole>
					</recordTarget>
				</xsl:for-each>
				<xsl:for-each select="$var1_act/ns0:participation[(fn:string(@typeCode) = 'ENT')]">
					<xsl:variable name="var12_role" as="node()?" select="ns0:role"/>
					<author>
						<time>
							<xsl:attribute name="nullFlavor" namespace="" select="'NI'"/>
						</time>
						<assignedAuthor>
							<id>
								<xsl:for-each select="$var12_role/ns0:id/ns0:II/ns0:root">
									<xsl:attribute name="root" namespace="" select="fn:string(.)"/>
								</xsl:for-each>
								<xsl:for-each select="$var12_role/ns0:id/ns0:II/ns0:extension">
									<xsl:attribute name="extension" namespace="" select="fn:string(.)"/>
								</xsl:for-each>
							</id>
						</assignedAuthor>
					</author>
				</xsl:for-each>
				<custodian>
					<assignedCustodian>
						<representedCustodianOrganization>
							<id>
								<xsl:attribute name="root" namespace="" select="'2.16.840.1.113883.3.26.1.6'"/>
							</id>
						</representedCustodianOrganization>
					</assignedCustodian>
				</custodian>
				<component>
					<structuredBody>
						<component>
							<section>
								<templateId>
									<xsl:attribute name="root" namespace="" select="'2.16.840.1.113883.10.20.1.16'"/>
								</templateId>
								<templateId>
									<xsl:attribute name="root" namespace="" select="'2.16.840.1.113883.3.88.11.83.119'"/>
								</templateId>
								<templateId>
									<xsl:attribute name="root" namespace="" select="'1.3.6.1.4.1.19376.1.5.3.1.3.25'"/>
								</templateId>
								<templateId>
									<xsl:attribute name="root" namespace="" select="'1.3.6.1.4.1.19376.1.5.3.1.1.5.3.2'"/>
								</templateId>
								<code>
									<xsl:attribute name="code" namespace="" select="'8716-3'"/>
									<xsl:sequence select="$var2_resultof_create_attribute"/>
									<xsl:sequence select="$var3_resultof_create_attribute"/>
									<xsl:attribute name="displayName" namespace="" select="'vital signs'"/>
								</code>
								<title>Physical Findings - Vital signs</title>
								<entry>
									<xsl:for-each select="$var1_act/ns0:relationship">
										<xsl:variable name="var25_cur" as="node()" select="."/>
										<xsl:for-each select="ns0:act[(fn:string($var25_cur/@name) = 'vitalSigns')]">
											<xsl:variable name="var13_resultof_create_element" as="node()">
												<effectiveTime>
													<xsl:attribute name="nullFlavor" namespace="" select="'NI'"/>
												</effectiveTime>
											</xsl:variable>
											<organizer>
												<xsl:attribute name="classCode" namespace="" select="'CLUSTER'"/>
												<xsl:attribute name="moodCode" namespace="" select="'EVN'"/>
												<templateId>
													<xsl:attribute name="root" namespace="" select="'2.16.840.1.113883.10.20.1.32'"/>
												</templateId>
												<templateId>
													<xsl:attribute name="root" namespace="" select="'2.16.840.1.113883.10.20.1.35'"/>
												</templateId>
												<templateId>
													<xsl:attribute name="root" namespace="" select="'1.3.6.1.4.1.19376.1.5.3.1.4.13.1'"/>
												</templateId>
												<xsl:for-each select="ns0:id/ns0:II">
													<id>
														<xsl:for-each select="ns0:root">
															<xsl:attribute name="root" namespace="" select="fn:string(.)"/>
														</xsl:for-each>
														<xsl:for-each select="ns0:extension">
															<xsl:attribute name="extension" namespace="" select="fn:string(.)"/>
														</xsl:for-each>
														<xsl:for-each select="ns0:assigningAuthorityName">
															<xsl:attribute name="assigningAuthorityName" namespace="" select="fn:string(.)"/>
														</xsl:for-each>
														<xsl:for-each select="ns0:displayable">
															<xsl:attribute name="displayable" namespace="" select="xs:string(xs:boolean(fn:string(.)))"/>
														</xsl:for-each>
													</id>
												</xsl:for-each>
												<code>
													<xsl:attribute name="code" namespace="" select="'46680005'"/>
													<xsl:attribute name="codeSystem" namespace="" select="'2.16.840.1.113883.6.96'"/>
													<xsl:attribute name="codeSystemName" namespace="" select="'SNOMED CT'"/>
													<xsl:attribute name="displayName" namespace="" select="'Vital signs'"/>
												</code>
												<statusCode>
													<xsl:attribute name="code" namespace="" select="'completed'"/>
												</statusCode>
												<xsl:sequence select="$var13_resultof_create_element"/>
												<xsl:for-each select="ns0:observation/ns0:value">
													<xsl:variable name="var24_cur" as="node()" select="."/>
													<xsl:variable name="var14_ST" as="node()?" select="ns0:ST"/>
													<xsl:variable name="var15_result" as="xs:boolean?">
														<xsl:for-each select="$var14_ST">
															<xsl:sequence select="(fn:exists($var24_cur/ns0:valueSet) or (fn:exists($var14_ST) and (fn:string-length(fn:string(.)) &gt; xs:decimal('0'))))"/>
														</xsl:for-each>
													</xsl:variable>
													<xsl:if test="fn:exists($var15_result[.])">
														<xsl:variable name="var16_label" as="node()?" select="ns0:label"/>
														<component>
															<observation>
																<xsl:attribute name="classCode" namespace="" select="'OBS'"/>
																<xsl:attribute name="moodCode" namespace="" select="'EVN'"/>
																<templateId>
																	<xsl:attribute name="root" namespace="" select="'2.16.840.1.113883.10.20.1.31'"/>
																</templateId>
																<templateId>
																	<xsl:attribute name="root" namespace="" select="'2.16.840.1.113883.3.88.11.83.14'"/>
																</templateId>
																<templateId>
																	<xsl:attribute name="root" namespace="" select="'1.3.6.1.4.1.19376.1.5.3.1.4.13'"/>
																</templateId>
																<templateId>
																	<xsl:attribute name="root" namespace="" select="'1.3.6.1.4.1.19376.1.5.3.1.4.13.2'"/>
																</templateId>
																<code>
																	<xsl:for-each select="$var16_label">
																		<xsl:variable name="var17_resultof_cast" as="xs:string" select="fn:string(.)"/>
																		<xsl:variable name="var18_resultof_equal" as="xs:boolean" select="($var17_resultof_cast = 'T: ')"/>
																		<xsl:if test="($var18_resultof_equal or (($var17_resultof_cast = 'P: ') or (($var17_resultof_cast = 'Wt: ') or (($var17_resultof_cast = 'R: ') or (($var17_resultof_cast = 'BP: ') or ($var17_resultof_cast = 'Ht: '))))))">
																			<xsl:attribute name="code" namespace="">
																				<xsl:choose>
																					<xsl:when test="$var18_resultof_equal">
																						<xsl:sequence select="'8310-5'"/>
																					</xsl:when>
																					<xsl:when test="($var17_resultof_cast = 'P: ')">
																						<xsl:sequence select="'8867-4'"/>
																					</xsl:when>
																					<xsl:when test="($var17_resultof_cast = 'Wt: ')">
																						<xsl:sequence select="'3141-9'"/>
																					</xsl:when>
																					<xsl:when test="($var17_resultof_cast = 'R: ')">
																						<xsl:sequence select="'9279-1'"/>
																					</xsl:when>
																					<xsl:when test="($var17_resultof_cast = 'BP: ')">
																						<xsl:sequence select="'8462-4'"/>
																					</xsl:when>
																					<xsl:otherwise>
																						<xsl:if test="($var17_resultof_cast = 'Ht: ')">
																							<xsl:sequence select="'8302-2'"/>
																						</xsl:if>
																					</xsl:otherwise>
																				</xsl:choose>
																			</xsl:attribute>
																		</xsl:if>
																	</xsl:for-each>
																	<xsl:sequence select="$var2_resultof_create_attribute"/>
																	<xsl:sequence select="$var3_resultof_create_attribute"/>
																	<xsl:if test="not($var16_label)">
																		<xsl:attribute name="code" namespace="">
																			<xsl:sequence select="8480-6"/>
																		</xsl:attribute>
																		<xsl:attribute name="displayName" namespace="">
																			<xsl:sequence select='"Systolic BP"'/>
																		</xsl:attribute>
																	</xsl:if>
																	<xsl:for-each select="$var16_label">
																		<xsl:variable name="var19_resultof_cast" as="xs:string" select="fn:string(.)"/>
																		<xsl:variable name="var20_resultof_equal" as="xs:boolean" select="($var19_resultof_cast = 'T: ')"/>
																		<xsl:if test="($var20_resultof_equal or (($var19_resultof_cast = 'P: ') or (($var19_resultof_cast = 'R: ') or (($var19_resultof_cast = 'Wt: ') or (($var19_resultof_cast = 'BP: ') or ($var19_resultof_cast = 'Ht: '))))))">
																			<xsl:attribute name="displayName" namespace="">
																				<xsl:choose>
																					<xsl:when test="$var20_resultof_equal">
																						<xsl:sequence select="'Body temperature'"/>
																					</xsl:when>
																					<xsl:when test="($var19_resultof_cast = 'P: ')">
																						<xsl:sequence select="'Heart rate'"/>
																					</xsl:when>
																					<xsl:when test="($var19_resultof_cast = 'R: ')">
																						<xsl:sequence select="'Respiration rate'"/>
																					</xsl:when>
																					<xsl:when test="($var19_resultof_cast = 'Wt: ')">
																						<xsl:sequence select="'Body weight'"/>
																					</xsl:when>
																					<xsl:when test="($var19_resultof_cast = 'BP: ')">
																						<xsl:sequence select="'Diastolic BP'"/>
																					</xsl:when>
																					<xsl:otherwise>
																						<xsl:if test="($var19_resultof_cast = 'Ht: ')">
																							<xsl:sequence select="'Body height'"/>
																						</xsl:if>
																					</xsl:otherwise>
																				</xsl:choose>
																			</xsl:attribute>
																		</xsl:if>
																	</xsl:for-each>
																</code>
																<statusCode>
																	<xsl:attribute name="code" namespace="" select="'completed'"/>
																</statusCode>
																<xsl:sequence select="$var13_resultof_create_element"/>
																<value>
																	<xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="xs:QName('PQ')"/>
																	<xsl:for-each select="$var14_ST">
																		<xsl:variable name="var21_resultof_cast" as="xs:string" select="fn:string(.)"/>
																		<xsl:if test="(fn:string-length($var21_resultof_cast) &gt; xs:decimal('0'))">
																			<xsl:attribute name="value" namespace="" select="$var21_resultof_cast"/>
																		</xsl:if>
																	</xsl:for-each>
																	<xsl:for-each select="$var16_label">
																		<xsl:variable name="var22_resultof_cast" as="xs:string" select="fn:string(.)"/>
																		<xsl:variable name="var23_resultof_equal" as="xs:boolean" select="($var22_resultof_cast = 'T: ')"/>
																		<xsl:if test="($var23_resultof_equal or (($var22_resultof_cast = 'P: ') or (($var22_resultof_cast = 'R: ') or (($var22_resultof_cast = 'Wt: ') or (($var22_resultof_cast = 'Ht: ') or ($var22_resultof_cast = 'BP: '))))))">
																			<xsl:attribute name="unit" namespace="">
																				<xsl:choose>
																					<xsl:when test="$var23_resultof_equal">
																						<xsl:sequence select="'Cel'"/>
																					</xsl:when>
																					<xsl:when test="($var22_resultof_cast = 'P: ')">
																						<xsl:sequence select="'min'"/>
																					</xsl:when>
																					<xsl:when test="($var22_resultof_cast = 'R: ')">
																						<xsl:sequence select="'min'"/>
																					</xsl:when>
																					<xsl:when test="($var22_resultof_cast = 'Wt: ')">
																						<xsl:sequence select="'kg'"/>
																					</xsl:when>
																					<xsl:when test="($var22_resultof_cast = 'Ht: ')">
																						<xsl:sequence select="'cm'"/>
																					</xsl:when>
																					<xsl:otherwise>
																						<xsl:if test="($var22_resultof_cast = 'BP: ')">
																							<xsl:sequence select="'mm[Hg]'"/>
																						</xsl:if>
																					</xsl:otherwise>
																				</xsl:choose>
																			</xsl:attribute>
																		</xsl:if>
																	</xsl:for-each>
																	<xsl:if test="not($var16_label)">
																		<xsl:attribute name="unit" namespace="">
																			<xsl:sequence select="'mm[Hg]'"/>
																		</xsl:attribute>
																	</xsl:if>
																</value>
															</observation>
														</component>
													</xsl:if>
												</xsl:for-each>
											</organizer>
										</xsl:for-each>
									</xsl:for-each>
								</entry>
							</section>
						</component>
					</structuredBody>
				</component>
			</xsl:for-each>
		</ClinicalDocument>
	</xsl:template>
</xsl:stylesheet>