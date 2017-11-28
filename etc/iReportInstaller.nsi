; Script generated by the HM NIS Edit Script Wizard.

; HM NIS Edit Wizard helper defines
;!define PRODUCT_NAME "iReport"
;!define PRODUCT_VERSION "1.2.3"
!define PRODUCT_PUBLISHER "Jaspersoft Corp."
;!define PRODUCT_WEB_SITE "http://ireport.sourceforge.net"
!define PRODUCT_DIR_REGKEY "Software\Microsoft\Windows\CurrentVersion\App Paths\${PRODUCT_NAME}-${PRODUCT_VERSION}.exe"
!define PRODUCT_UNINST_KEY "Software\Microsoft\Windows\CurrentVersion\Uninstall\${PRODUCT_NAME}-${PRODUCT_VERSION}.exe"
!define PRODUCT_UNINST_ROOT_KEY "HKLM"
!define PRODUCT_STARTMENU_REGVAL "NSIS:StartMenuDir"

!define UNINSTALL_SURVEY_URL "http://www.jaspersoft.com/surveys/iReportUninstall"

; MUI 1.67 compatible ------
!include "MUI.nsh"
!include fileassoc.nsh

; set execution level for Windows Vista
RequestExecutionLevel user

; MUI Settings
!define MUI_ABORTWARNING
!define MUI_ICON "${NSISDIR}\Contrib\Graphics\Icons\modern-install.ico"
!define MUI_UNICON "${NSISDIR}\Contrib\Graphics\Icons\modern-uninstall.ico"

; Welcome page
!insertmacro MUI_PAGE_WELCOME
; License page
!insertmacro MUI_PAGE_LICENSE "..\LICENSE_ireport.txt"
; Components page
!insertmacro MUI_PAGE_COMPONENTS
; Directory page
!insertmacro MUI_PAGE_DIRECTORY
; Start menu page
var ICONS_GROUP
!define MUI_STARTMENUPAGE_NODISABLE
!define MUI_STARTMENUPAGE_DEFAULTFOLDER "Jaspersoft\${PRODUCT_NAME}-${PRODUCT_VERSION}"
!define MUI_STARTMENUPAGE_REGISTRY_ROOT "${PRODUCT_UNINST_ROOT_KEY}"
!define MUI_STARTMENUPAGE_REGISTRY_KEY "${PRODUCT_UNINST_KEY}"
!define MUI_STARTMENUPAGE_REGISTRY_VALUENAME "${PRODUCT_STARTMENU_REGVAL}"
!insertmacro MUI_PAGE_STARTMENU Application $ICONS_GROUP
; Instfiles page
!insertmacro MUI_PAGE_INSTFILES
; Finish page
!define MUI_FINISHPAGE_RUN "$INSTDIR\bin\${PRODUCT_NAME}.exe"
!insertmacro MUI_PAGE_FINISH

; Uninstaller pages
!insertmacro MUI_UNPAGE_INSTFILES

; Language files
!insertmacro MUI_LANGUAGE "English"

; MUI end ------

Name "${PRODUCT_NAME} ${PRODUCT_VERSION}"
OutFile "..\dist\${PRODUCT_NAME}-${PRODUCT_VERSION}-windows-installer.exe"
InstallDir "$PROGRAMFILES\Jaspersoft\${PRODUCT_NAME}-${PRODUCT_VERSION}"
InstallDirRegKey HKLM "${PRODUCT_DIR_REGKEY}" ""
ShowInstDetails show
ShowUnInstDetails show

Section "${PRODUCT_NAME}" SEC01
  SetOutPath "$INSTDIR"
  SetOverwrite try
  File /r /x src "..\dist\${PRODUCT_NAME}-${PRODUCT_VERSION}-windows-installer\*.*"
;..\target\dist\${PRODUCT_NAME}-${PRODUCT_VERSION}-windows-installer\src\*.*

; Shortcuts
  !insertmacro MUI_STARTMENU_WRITE_BEGIN Application
  CreateDirectory "$SMPROGRAMS\$ICONS_GROUP"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\${PRODUCT_NAME}-${PRODUCT_VERSION}.lnk" "$INSTDIR\bin\${PRODUCT_NAME}.exe"
  CreateShortCut "$DESKTOP\${PRODUCT_NAME}-${PRODUCT_VERSION}.lnk" "$INSTDIR\bin\${PRODUCT_NAME}.exe"
  !insertmacro MUI_STARTMENU_WRITE_END
SectionEnd

;
;Section "Sources" SEC02
;
;  SetOutPath "$INSTDIR\src"
;  SetOverwrite try
;  File /r "..\target\dist\${PRODUCT_NAME}-${PRODUCT_VERSION}-windows-installer\src\*.*"

; Shortcuts
;  !insertmacro MUI_STARTMENU_WRITE_BEGIN Application
;  !insertmacro MUI_STARTMENU_WRITE_END
;SectionEnd

Section -AdditionalIcons
  SetOutPath $INSTDIR
  !insertmacro MUI_STARTMENU_WRITE_BEGIN Application
  
; CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\${PRODUCT_NAME} site.lnk" "$INSTDIR\${PRODUCT_NAME}.url" "URL"
;  WriteIniStr "$INSTDIR\${PRODUCT_NAME}.url" "InternetShortcut" "URL" "${PRODUCT_WEB_SITE}"
  WriteIniStr "$SMPROGRAMS\$ICONS_GROUP\${PRODUCT_NAME} site.url" "InternetShortcut" "URL" "${PRODUCT_WEB_SITE}"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Uninstall.lnk" "$INSTDIR\uninst.exe"
  !insertmacro MUI_STARTMENU_WRITE_END
SectionEnd

Section -Post
  WriteUninstaller "$INSTDIR\uninst.exe"
  WriteRegStr HKLM "${PRODUCT_DIR_REGKEY}" "" "$INSTDIR\${PRODUCT_NAME}.exe"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "DisplayName" "$(^Name)"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "UninstallString" "$INSTDIR\uninst.exe"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "DisplayIcon" "$INSTDIR\${PRODUCT_NAME}.exe"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "DisplayVersion" "${PRODUCT_VERSION}"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "URLInfoAbout" "${PRODUCT_WEB_SITE}"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "Publisher" "${PRODUCT_PUBLISHER}"
  !insertmacro APP_ASSOCIATE "jrxml" "iReport.Jrxml" "Jrxml source file" "$INSTDIR\bin\document.ico,0" "Open with ${PRODUCT_NAME}" "$INSTDIR\bin\${PRODUCT_NAME}.exe $\"%1$\""
SectionEnd

; Section descriptions
!insertmacro MUI_FUNCTION_DESCRIPTION_BEGIN
  !insertmacro MUI_DESCRIPTION_TEXT ${SEC01} ""
;  !insertmacro MUI_DESCRIPTION_TEXT ${SEC02} ""
!insertmacro MUI_FUNCTION_DESCRIPTION_END


Function un.onUninstSuccess
  HideWindow
  MessageBox MB_ICONINFORMATION|MB_OK "$(^Name) was successfully removed from your computer."
FunctionEnd

Function un.onInit
  MessageBox MB_ICONQUESTION|MB_YESNO|MB_DEFBUTTON2 "Are you sure you want to completely remove $(^Name) and all of its components?" IDYES +2
  Abort
FunctionEnd

Section Uninstall
  !insertmacro MUI_STARTMENU_GETFOLDER "Application" $ICONS_GROUP
  StrCmp $ICONS_GROUP "" NO_SHORTCUTS
  RMDir /r /REBOOTOK $INSTDIR

  Delete "$SMPROGRAMS\$ICONS_GROUP\Uninstall.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\${PRODUCT_NAME} site.url"
  Delete "$DESKTOP\${PRODUCT_NAME}-${PRODUCT_VERSION}.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\${PRODUCT_NAME}-${PRODUCT_VERSION}.lnk"
  !insertmacro APP_UNASSOCIATE "jrxml" "iReport.Jrxml"

  RMDir "$SMPROGRAMS\$ICONS_GROUP"
  RMDir "$SMPROGRAMS\Jaspersoft"

  NO_SHORTCUTS:
  
  MessageBox MB_YESNO "Do you want to delete the ${PRODUCT_NAME} configuration files too? ($APPDATA\.${PRODUCT_NAME}\${PRODUCT_VERSION})" IDNO configDone
    RMDir /r /REBOOTOK "$APPDATA\.${PRODUCT_NAME}\${PRODUCT_VERSION}"
  configDone:

  DeleteRegKey ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}"
  DeleteRegKey HKLM "${PRODUCT_DIR_REGKEY}"
  SetAutoClose true

  StrCpy $0 "${UNINSTALL_SURVEY_URL}"
  Call un.openLinkNewWindow
SectionEnd


# Uses $0
Function un.openLinkNewWindow
  Push $3
  Push $2
  Push $1
  Push $0
  ReadRegStr $0 HKCR "http\shell\open\command" ""
# Get browser path
    DetailPrint $0
  StrCpy $2 '"'
  StrCpy $1 $0 1
  StrCmp $1 $2 +2 # if path is not enclosed in " look for space as final char
    StrCpy $2 ' '
  StrCpy $3 1
  loop:
    StrCpy $1 $0 1 $3
    DetailPrint $1
    StrCmp $1 $2 found
    StrCmp $1 "" found
    IntOp $3 $3 + 1
    Goto loop

  found:
    StrCpy $1 $0 $3
    StrCmp $2 " " +2
      StrCpy $1 '$1"'

  Pop $0
  Exec '$1 $0'
  Pop $1
  Pop $2
  Pop $3
FunctionEnd