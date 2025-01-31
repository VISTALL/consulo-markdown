/*
 * Copyright 2000-2015 JetBrains s.r.o.
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
package org.intellij.plugins.markdown.lang.parser;


import consulo.annotation.component.ExtensionImpl;
import consulo.language.Language;
import consulo.language.ast.ASTNode;
import consulo.language.ast.IFileElementType;
import consulo.language.ast.TokenSet;
import consulo.language.file.FileViewProvider;
import consulo.language.lexer.Lexer;
import consulo.language.parser.ParserDefinition;
import consulo.language.parser.PsiParser;
import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiFile;
import consulo.language.version.LanguageVersion;
import org.intellij.plugins.markdown.lang.MarkdownElementTypes;
import org.intellij.plugins.markdown.lang.MarkdownLanguage;
import org.intellij.plugins.markdown.lang.lexer.MarkdownToplevelLexer;
import org.intellij.plugins.markdown.lang.psi.MarkdownPsiFactory;
import org.intellij.plugins.markdown.lang.psi.impl.MarkdownFile;
import jakarta.annotation.Nonnull;

@ExtensionImpl
public class MarkdownParserDefinition implements ParserDefinition {

  @Nonnull
  @Override
  public Language getLanguage() {
    return MarkdownLanguage.INSTANCE;
  }

  @Nonnull
  @Override
  public Lexer createLexer(@Nonnull LanguageVersion languageVersion) {
    return new MarkdownToplevelLexer();
  }

  @Nonnull
  @Override
  public PsiParser createParser(@Nonnull LanguageVersion languageVersion) {
    return new MarkdownParserAdapter();
  }

  @Nonnull
  @Override
  public IFileElementType getFileNodeType() {
    return MarkdownElementTypes.MARKDOWN_FILE_ELEMENT_TYPE;
  }

  @Nonnull
  @Override
  public TokenSet getWhitespaceTokens(@Nonnull LanguageVersion languageVersion) {
    return TokenSet.create();
  }

  @Nonnull
  @Override
  public TokenSet getCommentTokens(@Nonnull LanguageVersion languageVersion) {
    return TokenSet.EMPTY;
  }

  @Nonnull
  @Override
  public TokenSet getStringLiteralElements(@Nonnull LanguageVersion languageVersion) {
    return TokenSet.EMPTY;
  }

  @Nonnull
  @Override
  public PsiElement createElement(ASTNode node) {
    return MarkdownPsiFactory.INSTANCE.createElement(node);
  }

  @Nonnull
  @Override
  public PsiFile createFile(FileViewProvider viewProvider) {
    return new MarkdownFile(viewProvider);
  }

  @Nonnull
  @Override
  public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
    return SpaceRequirements.MAY;
  }
}
