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
package git4idea.repo;

import com.intellij.dvcs.repo.Repository;
import git4idea.GitLocalBranch;
import git4idea.GitRemoteBranch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Kirill Likhodedov
 */
public class GitRepoInfo {

  @Nullable private final GitLocalBranch myCurrentBranch;
  @Nullable private final String myCurrentRevision;
  @NotNull private final Repository.State myState;
  @NotNull private final Set<GitRemote> myRemotes;
  @NotNull private final Set<GitLocalBranch> myLocalBranches;
  @NotNull private final Set<GitRemoteBranch> myRemoteBranches;
  @NotNull private final Set<GitBranchTrackInfo> myBranchTrackInfos;

  public GitRepoInfo(@Nullable GitLocalBranch currentBranch, @Nullable String currentRevision, @NotNull Repository.State state,
                     @NotNull Collection<GitRemote> remotes, @NotNull Collection<GitLocalBranch> localBranches,
                     @NotNull Collection<GitRemoteBranch> remoteBranches, @NotNull Collection<GitBranchTrackInfo> branchTrackInfos) {
    myCurrentBranch = currentBranch;
    myCurrentRevision = currentRevision;
    myState = state;
    myRemotes = new LinkedHashSet<GitRemote>(remotes);
    myLocalBranches = new LinkedHashSet<GitLocalBranch>(localBranches);
    myRemoteBranches = new LinkedHashSet<GitRemoteBranch>(remoteBranches);
    myBranchTrackInfos = new LinkedHashSet<GitBranchTrackInfo>(branchTrackInfos);
  }

  @Nullable
  public GitLocalBranch getCurrentBranch() {
    return myCurrentBranch;
  }

  @NotNull
  public Collection<GitRemote> getRemotes() {
    return myRemotes;
  }

  @NotNull
  public Collection<GitLocalBranch> getLocalBranches() {
    return myLocalBranches;
  }

  @NotNull
  public Collection<GitRemoteBranch> getRemoteBranches() {
    return myRemoteBranches;
  }

  @NotNull
  public Collection<GitBranchTrackInfo> getBranchTrackInfos() {
    return myBranchTrackInfos;
  }

  @Nullable
  public String getCurrentRevision() {
    return myCurrentRevision;
  }

  @NotNull
  public Repository.State getState() {
    return myState;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    GitRepoInfo info = (GitRepoInfo)o;

    if (myState != info.myState) return false;
    if (myCurrentRevision != null ? !myCurrentRevision.equals(info.myCurrentRevision) : info.myCurrentRevision != null) return false;
    if (myCurrentBranch != null ? !myCurrentBranch.equals(info.myCurrentBranch) : info.myCurrentBranch != null) return false;
    if (!myRemotes.equals(info.myRemotes)) return false;
    if (!myBranchTrackInfos.equals(info.myBranchTrackInfos)) return false;
    if (!myLocalBranches.equals(info.myLocalBranches)) return false;
    if (!myRemoteBranches.equals(info.myRemoteBranches)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = myCurrentBranch != null ? myCurrentBranch.hashCode() : 0;
    result = 31 * result + (myCurrentRevision != null ? myCurrentRevision.hashCode() : 0);
    result = 31 * result + myState.hashCode();
    result = 31 * result + myRemotes.hashCode();
    result = 31 * result + myLocalBranches.hashCode();
    result = 31 * result + myRemoteBranches.hashCode();
    result = 31 * result + myBranchTrackInfos.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return String.format("GitRepoInfo{current=%s, remotes=%s, localBranches=%s, remoteBranches=%s, trackInfos=%s}",
                         myCurrentBranch, myRemotes, myLocalBranches, myRemoteBranches, myBranchTrackInfos);
  }
}
