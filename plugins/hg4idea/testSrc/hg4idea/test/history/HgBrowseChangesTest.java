/*
 * Copyright 2000-2013 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hg4idea.test.history;

import com.intellij.openapi.vcs.CachingCommittedChangesProvider;
import com.intellij.openapi.vcs.VcsException;
import com.intellij.openapi.vcs.versionBrowser.ChangeBrowserSettings;
import com.intellij.openapi.vcs.versionBrowser.CommittedChangeList;
import hg4idea.test.HgPlatformTest;
import org.zmlx.hg4idea.HgVcs;
import org.zmlx.hg4idea.execution.HgCommandException;
import org.zmlx.hg4idea.provider.HgRepositoryLocation;

import java.text.ParseException;
import java.util.List;

import static com.intellij.dvcs.test.Executor.cd;
import static com.intellij.dvcs.test.Executor.touch;
import static hg4idea.test.HgExecutor.hg;

/**
 * @author Nadya Zabrodina
 */
public class HgBrowseChangesTest extends HgPlatformTest {

  private HgVcs myVcs;
  private ChangeBrowserSettings mySettings;
  private String dateBefore;
  private String dateAfter;

  @Override
  public void setUp() throws Exception {
    super.setUp();
    myVcs = HgVcs.getInstance(myProject);
    assert myVcs != null;
    mySettings = new ChangeBrowserSettings();
    cd(myRepository);
    touch("f2.txt");
    hg("add");
    hg("commit -m add");
    java.util.Calendar now = java.util.Calendar.getInstance();
    dateBefore = ChangeBrowserSettings.DATE_FORMAT.format(now.getTime());
    now.set(java.util.Calendar.YEAR, 1970);
    dateAfter = ChangeBrowserSettings.DATE_FORMAT.format(now.getTime());
  }

  public void testLogRevisionWithDataFilter() throws HgCommandException, ParseException, VcsException {
    mySettings.USE_DATE_AFTER_FILTER = true;
    mySettings.USE_DATE_BEFORE_FILTER = true;
    mySettings.DATE_BEFORE = dateBefore;
    mySettings.DATE_AFTER = dateAfter;
    doTest();
  }

  public void testLogRevisionWithAfterDataFilter() throws HgCommandException, ParseException, VcsException {
    mySettings.USE_DATE_AFTER_FILTER = true;
    mySettings.DATE_AFTER = dateAfter;
    doTest();
  }

  public void testLogRevisionWithBeforeDataFilter() throws HgCommandException, ParseException, VcsException {
    mySettings.USE_DATE_BEFORE_FILTER = true;
    mySettings.DATE_BEFORE = dateBefore;
    doTest();
  }

  public void testLogRevisionWithBeforeFilter() throws HgCommandException, ParseException, VcsException {

    mySettings.USE_CHANGE_BEFORE_FILTER = true;
    mySettings.CHANGE_BEFORE = "1";
    doTest();
  }

  public void testLogRevisionWithAfterFilter() throws HgCommandException, ParseException, VcsException {
    mySettings.USE_CHANGE_AFTER_FILTER = true;
    mySettings.CHANGE_AFTER = "0";
    doTest();
  }

  public void testLogRevisionWithFilter() throws HgCommandException, ParseException, VcsException {

    mySettings.USE_CHANGE_BEFORE_FILTER = true;
    mySettings.USE_CHANGE_AFTER_FILTER = true;
    mySettings.USE_DATE_AFTER_FILTER = true;
    mySettings.USE_DATE_BEFORE_FILTER = true;
    mySettings.DATE_BEFORE = dateBefore;
    mySettings.DATE_AFTER = dateAfter;
    mySettings.CHANGE_BEFORE = "1";
    mySettings.CHANGE_AFTER = "0";
    doTest();
  }

  private void doTest() throws HgCommandException, VcsException {
    CachingCommittedChangesProvider provider = myVcs.getCachingCommittedChangesProvider();
    assert provider != null;
    //noinspection unchecked
    List<CommittedChangeList> revisions =
      provider.getCommittedChanges(mySettings, new HgRepositoryLocation(myRepository.getUrl(), myRepository), -1);
    assertTrue(!revisions.isEmpty());
  }
}
